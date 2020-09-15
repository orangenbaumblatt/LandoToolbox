package com.github.orangenbaumblatt.landotoolbox.actions

import com.github.orangenbaumblatt.landotoolbox.LandoToolingItem
import com.github.orangenbaumblatt.landotoolbox.LandoToolingListPopupStep
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.psi.PsiManager

class LandoToolingAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null) return
        val basePath = e.project?.basePath ?: ""

        val fileSystem = VirtualFileManager.getInstance().getFileSystem("file")
        val landoBaseFile: VirtualFile? = fileSystem.findFileByPath("$basePath/.lando.base.yml")
        val landoFile: VirtualFile? = fileSystem.findFileByPath("$basePath/.lando.yml")

        val popup = JBPopupFactory.getInstance()
            .createListPopup(
                LandoToolingListPopupStep(
                "Run Lando Tooling Command",
                commandList(landoBaseFile, e.project) + commandList(landoFile, e.project)
                )
            )
        popup.showInFocusCenter()
    }

    companion object {
        private fun commandList(file: VirtualFile?, project: Project?): List<LandoToolingItem> {
            if (file == null || project == null) return listOf()

            val psi = PsiManager.getInstance(project).findFile(file)
            val commandsList = psi?.firstChild?.firstChild?.children?.firstOrNull {
                el -> el.text.contains("tooling")
            }?.lastChild?.children?.map { el -> el.firstChild.text }
            return commandsList?.map { el -> LandoToolingItem(project, el) } ?: listOf()
        }
    }
}
