package com.github.orangenbaumblatt.landotoolbox.lineMarker

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor
import com.intellij.psi.PsiElement
import org.jetbrains.yaml.YAMLTokenTypes
import org.jetbrains.yaml.psi.YAMLKeyValue
import org.jetbrains.yaml.psi.impl.YAMLKeyValueImpl

class LandoRunLineMarkerContributor : LineMarkerProviderDescriptor() {
    override fun getName(): String? {
        return "Lando Tooling"
    }

    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element.node.elementType != YAMLTokenTypes.SCALAR_KEY) {
            return null
        }

        val yamlKeyValue = element.parent
        val toolingParent = element.parent.parent.parent
        if (yamlKeyValue !is YAMLKeyValue || toolingParent !is YAMLKeyValueImpl || toolingParent.name != "tooling") {
            return null
        }

        return SectionLineMarkerInfo(
                element,
                element.textRange,
                null
        )
    }
}

