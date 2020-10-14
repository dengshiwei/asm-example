package com.andoter.asm_plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class AndoterPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        println("asm_plugin")
    }
}