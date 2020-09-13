package com.github.orangenbaumblatt.landotoolbox.proxyLineMarker

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.psi.PsiElement
import org.jetbrains.yaml.YAMLTokenTypes
import org.jetbrains.yaml.psi.impl.YAMLKeyValueImpl
import org.jetbrains.yaml.psi.impl.YAMLPlainTextImpl

class ProxyLineMarkerContributor : LineMarkerProviderDescriptor() {
    override fun getName(): String? {
        return "Lando Proxy Links"
    }

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element.node.elementType != YAMLTokenTypes.TEXT) {
            return null
        }

        val yamlKeyValue = element.parent
        val proxyParent = element.parent.parent.parent.parent.parent.parent
        if (yamlKeyValue !is YAMLPlainTextImpl || proxyParent !is YAMLKeyValueImpl || proxyParent.name != "proxy") {
            return null
        }

        return ProxyLineMarkerInfo(
                element,
                element.textRange,
                null
        )
    }
}

