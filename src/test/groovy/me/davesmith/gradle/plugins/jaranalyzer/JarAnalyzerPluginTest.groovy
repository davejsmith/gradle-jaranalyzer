package me.davesmith.gradle.plugins.jaranalyzer

import me.davesmith.gradle.plugins.jaranalyzer.JarAnalyzerPlugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class JarAnalyzerPluginTest {


    @Test
    public void greeterPluginAddsGreetingTaskToProject() {

        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'jaranalyzer'

        assert project.plugins.getPlugin(JarAnalyzerPlugin)
    }
}
