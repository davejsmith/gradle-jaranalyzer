package me.davesmith.gradle.plugins.jaranalyzer

import com.kirkk.analyzer.textui.Summary
import com.kirkk.analyzer.textui.XMLUISummary
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

class JarAnalyzerReportTask extends DefaultTask {


    @OutputDirectory
    File outputDir = new File(project.buildDir,"reports/jaranalyzer")

    File jarDir = new File("${project.buildDir.path}","jars")

    @OutputFile
    File xmlReport = new File(outputDir,'jaranalyzer.xml')

    @TaskAction
    def analyze() {

        JarAnalyzerExtension jarAnalyzerExtension = project.extensions.jaranalyzer

        project.subprojects.each { p ->
            project.copy {
                from p.jar.archivePath
                into "${project.buildDir.path}/jars"
            }
        }

        if (jarAnalyzerExtension.xml) {
            Summary summary = new XMLUISummary()
            summary.createSummary(jarDir, xmlReport, jarAnalyzerExtension.packageFilter, jarAnalyzerExtension.jarFilter);

        }
    }


}
