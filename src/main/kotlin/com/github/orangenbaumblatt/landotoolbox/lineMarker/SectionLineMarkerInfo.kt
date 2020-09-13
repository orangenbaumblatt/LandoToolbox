package com.github.orangenbaumblatt.landotoolbox.lineMarker

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.ActionGroup
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.util.Function

class SectionLineMarkerInfo(
        element: PsiElement,
        range: TextRange,
        tooltipProvider: Function<in PsiElement?, String?>?
) : LineMarkerInfo<PsiElement?>(
        element,
        range,
        AllIcons.RunConfigurations.TestState.Run,
        tooltipProvider,
        null,
        GutterIconRenderer.Alignment.LEFT
) {
    override fun createGutterRenderer(): GutterIconRenderer? {
        return object : LineMarkerGutterIconRenderer<PsiElement?>(this) {
            override fun getClickAction(): AnAction? {
                return null
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