package me.davesmith.gradle.plugins.jaranalyzer

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Before
import org.junit.Test

class JarAnalyzerPluginTest {

    Project project
    Project child

    @Before
    public void setUp() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'java'
        child = ProjectBuilder.builder().withName('child').withParent(project).build()
        child.apply plugin: 'groovy'
    }

    @Test
    public void pluginAddsTaskToProject() {

        project.apply plugin: 'jaranalyzer'

        child.evaluate()
        project.evaluate()

        assert project.extensions.getByType(JarAnalyzerExtension)
        assert project.plugins.getPlugin(JarAnalyzerPlugin)
        assert project.tasks.getByName('jarAnalyzerReport')
        println project.tasks.getByName('jarAnalyzerReport').outputDir

    }
}
