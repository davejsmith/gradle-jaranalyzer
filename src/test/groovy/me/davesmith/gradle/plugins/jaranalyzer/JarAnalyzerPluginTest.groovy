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
