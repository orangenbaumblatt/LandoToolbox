package com.github.orangenbaumblatt.landotoolbox.actions

import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

@Suppress("ComponentNotRegistered")
class OpenInBrowserAction(private val url: String?) : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if (url == null) return
        BrowserUtil.browse("http://$url")
    }
}