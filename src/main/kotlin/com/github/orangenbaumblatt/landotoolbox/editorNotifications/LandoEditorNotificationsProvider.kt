package com.github.orangenbaumblatt.landotoolbox.editorNotifications

import com.github.orangenbaumblatt.landotoolbox.util.LandofileUtil
import com.intellij.openapi.editor.colors.EditorColors
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EditorNotificationPanel
import com.intellij.ui.EditorNotifications


class LandoEditorNotificationsProvider : EditorNotifications.Provider<EditorNotificationPanel>() {

    override fun getKey(): Key<EditorNotificationPanel> {
        return Key.create("LandoEditorNotificationsProvider")
    }

    override fun createNotificationPanel(file: VirtualFile, fileEditor: FileEditor, project: Project): EditorNotificationPanel? {
        if (!LandofileUtil.isLandofile(file)) return null

        val panel = EditorNotificationPanel(EditorColors.GUTTER_BACKGROUND).text("This is a Lando configuration file")
        panel.createActionLabel("Start Lando", "com.github.orangenbaumblatt.landotoolbox.actions.LandoToolingAction")
        return panel
    }
}
