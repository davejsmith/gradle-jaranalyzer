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

class JarAnalyzerExtension {

    /** Generate dot report? */
    boolean dot = false
    /** Generate xml report? */
    boolean xml = true
    /** Generate html report? */
    boolean html = true

    /** list of jars to exclude from analysis. */
    List<String> jarFilter = []

    /** If all subproject jars start with the same prefix, then set this
     * value to be prefix.  The prefix will be removed from the jar name
     * prior to analysis (reducing the size of the dot diagram).
     */
    String jarPrefix = ""

    /** List of glob package patterns to exclude from analysis. */
    List<String> packageFilter = [
            'javax.*',
            'java.*', 'org.omg*', 'org.ietf.*', 'org.w3c.*', 'org.xml.sax*', 'sun.*', 'sunw.*', 'com.sun.*',
            'groovy.*', 'org.codehaus.groovy.*'
    ]

}
