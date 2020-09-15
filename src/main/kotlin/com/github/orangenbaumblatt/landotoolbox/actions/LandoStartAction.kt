package com.github.orangenbaumblatt.landotoolbox.actions

import com.intellij.execution.actions.StopProcessAction
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.ToolWindowId
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.content.MessageView
import java.nio.charset.Charset

class LandoStartAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        if (e.project == null) return

        val project = e.project

        val cmds = listOf("lando", "start")
        val generalCommandLine = GeneralCommandLine(cmds)
        generalCommandLine.charset = Charset.forName("UTF-8")
        generalCommandLine.setWorkDirectory(project!!.basePath)

        val processHandler = OSProcessHandler(generalCommandLine)

        processHandler.startNotify()

        val console = createConsole(project)

        val actionToolbar = createToolWindowActionsBar(console)

        val toolWindowPanel = SimpleToolWindowPanel(false, false)
        toolWindowPanel.setContent(console.component)
        toolWindowPanel.toolbar = actionToolbar.component

        val content = ContentFactory.SERVICE.getInstance().createContent(toolWindowPanel.component, "Lando Console", true)
        Disposer.register(content, console)

        val contentManager = MessageView.SERVICE.getInstance(project).contentManager
        contentManager.addContent(content)
        contentManager.setSelectedContent(content)

        val toolWindow = ToolWindowManager.getInstance(project).getToolWindow(ToolWindowId.MESSAGES_WINDOW)
        toolWindow?.activate(null, true)

        processHandler.addProcessListener(object : ProcessAdapter() {
            override fun processTerminated(event: ProcessEvent) {
                console.print("Lando process ended with code ${event.exitCode}", ConsoleViewContentType.SYSTEM_OUTPUT)
            }
        })

        console.attachToProcess(processHandler)
    }

    private fun createToolWindowActionsBar(console: ConsoleView): ActionToolbar {
        val actionGroup = DefaultActionGroup()

        val stopProcessAction = StopProcessAction("Stop command", "Stop test", null)
        actionGroup.addAction(stopProcessAction)

        val toolbar = ActionManager.getInstance().createActionToolbar("LandoAction", actionGroup, false)
        toolbar.setTargetComponent(console.component)

        return toolbar
    }

    private fun createConsole(project: Project): ConsoleView {
        val consoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project)
        consoleBuilder.setViewer(true)
        return consoleBuilder.console
    }
}
