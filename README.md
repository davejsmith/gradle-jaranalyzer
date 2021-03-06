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

The plugin can produce the DOT file but does not attempt to convert it to an image.  You'll need
GraphViz installed to manually generate the dependency graphics. For example, to convert the DOT into an image:

```
  dot -Tpng build/reports/jaranalyzer/jaranalyzer.dot -O
```


Configuration
--------------

THe plugin is configured using an extension object in the root project. Below are the properties available
with default settings:

```
  jaranalyzer {

    dot = false
    xml = true
    html = true
    osgi = false

    jarPrefix = ""

    jarFilter = []
    packageFilter = [ 'javax.*', 'java.*', 'org.omg*', 'org.ietf.*', 'org.w3c.*', 'org.xml.sax*', 'sun.*',
                      'sunw.*', 'com.sun.*', 'groovy.*', 'org.codehaus.groovy*', 'groovyjarjarasm.*','org.slf4j*' ]

    configuration = 'runtime'

   }
```

To add to the filters, without replacing the defaults, use groovy notation:

```
   jarAnalyzer {
       packageFilter += 'org.log4j*'
   }
```



Notes
------

* JarAnalyzer's API assumes that jars to be analyzed are all in one directory.  Therefore, the jars are
copied from each subproject into `build/jars`.
* While copying, the jars are renamed to shorten the file names.  If your subprojects
contain a common prefix, you can set `jaranalyzer.jarPrefex`. The prefix will also be removed from the
jar file names.  Shorter names make the DOT graphics more readable.

ChangeLog
---------

### 0.1.0.SNAPSHOT
* Initial version with basic support for JarAnalyzer's reports

### 0.2.0.SNAPSHOT

* Added a simple "OSGI" report.  Retrieves the 'Bundle-SymbolicName' from each jar in the 'runtime' (by default) configuration
and produces a simple (tab-delimited) report called 'osgi-report.txt'.  Useful for determining a project's "OSGI readiness".