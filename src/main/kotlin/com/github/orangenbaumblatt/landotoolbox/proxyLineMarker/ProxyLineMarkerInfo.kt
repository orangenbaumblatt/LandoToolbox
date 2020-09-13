package com.github.orangenbaumblatt.landotoolbox.proxyLineMarker

import com.github.orangenbaumblatt.landotoolbox.actions.OpenInBrowserAction
import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.util.Function

class ProxyLineMarkerInfo(
        element: PsiElement,
        range: TextRange,
        tooltipProvider: Function<in PsiElement?, String?>?
) : LineMarkerInfo<PsiElement?>(
        element,
        range,
        AllIcons.General.Web,
        tooltipProvider,
        null,
        GutterIconRenderer.Alignment.LEFT
) {
    override fun createGutterRenderer(): GutterIconRenderer? {
        return object : LineMarkerGutterIconRenderer<PsiElement?>(this) {
            override fun getClickAction(): AnAction? {
                if (element == null) return null
                return OpenInBrowserAction(element?.text)
            }

            override fun isNavigateAction(): Boolean {
                return true
            }

            override fun getPopupMenuActions(): ActionGroup? {
                return null
            }
        }
    }
}