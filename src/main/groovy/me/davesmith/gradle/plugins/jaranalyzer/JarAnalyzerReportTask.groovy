/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.davesmith.gradle.plugins.jaranalyzer

import com.kirkk.analyzer.textui.DOTSummary
import com.kirkk.analyzer.textui.XMLUISummary
import me.davesmith.gradle.plugins.jaranalyzer.internal.JarAnalyzerXslt
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.util.jar.JarFile

class JarAnalyzerReportTask extends DefaultTask {


    @OutputDirectory
    File outputDir = new File(project.buildDir, "reports/jaranalyzer")

    File jarDir = new File("${project.buildDir.path}", "jars")

    @OutputFile
    File xmlReport = new File(outputDir, 'jaranalyzer.xml')

    @OutputFile
    File htmlReport = new File(outputDir, 'jaranalyzer.html')

    @OutputFile
    File dotReport = new File(outputDir, 'jaranalyzer.dot')

    @OutputFile
    File osgiReport = new File(outputDir, 'osgi-report.txt')

    @TaskAction
    def analyze() {

        JarAnalyzerExtension jarAnalyzerExtension = project.extensions.jaranalyzer

        project.subprojects.each { p ->
            project.copy {
                from p.jar.archivePath
                into "${project.buildDir.path}/jars"
                rename "(.*)", "${p.name}.jar" - jarAnalyzerExtension.jarPrefix
            }
        }

        if (jarAnalyzerExtension.xml || jarAnalyzerExtension.html) {
            new XMLUISummary().createSummary(jarDir, xmlReport, jarAnalyzerExtension.packageFilter.join(';'), jarAnalyzerExtension.jarFilter.join(';'));
        }

        if (jarAnalyzerExtension.html) {
            ant.xslt(in: xmlReport, out: htmlReport) {
                style {
                    string(value: "${JarAnalyzerXslt.text}")
                }
                param(name: "today", expression: "${new Date()}")
            }
        }

        if (jarAnalyzerExtension.dot) {
            new DOTSummary().createSummary(jarDir, dotReport, jarAnalyzerExtension.packageFilter.join(';'), jarAnalyzerExtension.jarFilter.join(';'));
        }

        if (jarAnalyzerExtension.osgi) {

            Map<String, String> report = new TreeMap()

            project.subprojects.each { p ->
                p.configurations[jarAnalyzerExtension.configuration].files.each { f ->
                    def manifest = new JarFile(f)?.getManifest()
                    report[f.getName()] = manifest == null ? '<missing manifest>' : manifest?.getMainAttributes()?.getValue('Bundle-SymbolicName')
                }
            }

            osgiReport.withPrintWriter { writer ->
                report.each { k, v ->
                    writer.println "$k\t$v"
                }
            }
        }

    }

}
