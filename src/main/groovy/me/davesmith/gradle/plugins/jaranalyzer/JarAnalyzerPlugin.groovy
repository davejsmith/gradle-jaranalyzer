package me.davesmith.gradle.plugins.jaranalyzer

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

class JarAnalyzerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        project.extensions.create("jaranalyzer", JarAnalyzerExtension)

        project.tasks.create("jarAnalyzerReport",JarAnalyzerReportTask)

        project.afterEvaluate { Project p ->
            p.tasks.getByName('jarAnalyzerReport').dependsOn p.subprojects.collect { Project targetProject ->
                targetProject.tasks.withType(Jar)
            }.flatten()

        }
    }

}
