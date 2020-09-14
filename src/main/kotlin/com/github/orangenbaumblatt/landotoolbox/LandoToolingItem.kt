package com.github.orangenbaumblatt.landotoolbox

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.terminal.JBTerminalWidget
import java.awt.BorderLayout
import java.awt.Dimension
import java.nio.charset.Charset
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class LandoToolingItem(
        private val project: Project?,
        private val command: String,
        private val separator: String? = null
) {

    fun getText(): String {
        return command
    }

    fun getSeparatorText(): String? {
        return separator
    }

    fun onChosen() {
        val cmds = listOf("lando", command)
        val generalCommandLine = GeneralCommandLine(cmds)
        generalCommandLine.charset = Charset.forName("UTF-8")
        generalCommandLine.setWorkDirectory(project!!.basePath)

        val processHandler = OSProcessHandler(generalCommandLine)

        processHandler.addProcessListener(object: ProcessAdapter() {
            override fun processTerminated(event: ProcessEvent) {
                if (event.exitCode == 0) {

                }
            }
        })

        processHandler.startNotify()
    }
}
