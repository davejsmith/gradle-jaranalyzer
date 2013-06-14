gradle-jaranalyzer
==================

Gradle plugin for Kirk Knoernschild's JarAnalyzer (http://www.kirkk.com/main/Main/JarAnalyzer)

Introduction
------------

Kirk's JarAnalyzer analyzes a directory of jar files and produces dependency metrics between the jars.
See link above fo more information.

The plugin provides analysis of jar files produced by a Gradle multi-project build.  All jars produced by
a project are analyzed as a group.  Reports are provided in XML, HTML and DOT.

Usage
-----

```
buildscript {
    repositories {  maven { url 'http://repos.ssdt.nwoca.org/artifactory/repo' }  }
    dependencies { classpath 'me.davesmith:jaranalyzerplugin:0.1.0-SNAPSHOT' }
}

apply plugin: 'jaranalyzer'  // in a build script
apply plugin: me.davesmith.gradle.plugins.jaranalyzer.JarAnalyzerPlugin // in an init script

```

The plugin should only be applied to a root project of a multi-project build.
It adds a single task: `jarAnalyzerReport` which depends on the Jar tasks from each subproject.
Reports are placed in `build/reports/jaranalyzer`

Configuration
--------------

THe plugin is configured using an extension object in the root project. Below are the properties available
with default settings:

```
 jaranalyzer {

    dot = false
    xml = true
    html = true

    jarPrefix = ""

    jarFilter = []
    packageFilter = [ 'javax.*', 'java.*', 'org.omg*', 'org.ietf.*', 'org.w3c.*', 'org.xml.sax*', 'sun.*', 'sunw.*', 'com.sun.*', 'groovy.*', 'org.codehaus.groovy*', 'groovyjarjarasm.*','org.slf4j*'    ]

 }

```

> Note: The plugin can produce the DOT file but does not attempt to convert it to an image.  You'll need
Graphviz installed to manually generate the dependency graphics.

Notes
------

* JarAnalyzer's API assumes that jars to be analyzed are all in one directory.  Therefore, the jars are
copied from each subproject into `build/jars`.
* While copying, the jars are renamed shorten the names.  If your subprojects
contain a common prefix, you can set `jaranalyzer.jarPrefex`. The prefix will also be removed from the
jar file names.  Shorter names make the DOT graphics more readable.

