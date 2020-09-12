package com.github.orangenbaumblatt.landotoolbox.services

import com.intellij.openapi.project.Project
import com.github.orangenbaumblatt.landotoolbox.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
