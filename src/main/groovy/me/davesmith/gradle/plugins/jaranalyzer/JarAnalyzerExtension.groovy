package me.davesmith.gradle.plugins.jaranalyzer

class JarAnalyzerExtension {

    boolean dot = false
    boolean xml = true
    boolean html = true

    String jarFilter = ""

    List<String> packageFilter = [
            'javax.*',
            'java.*', 'org.omg*', 'org.ietf.*', 'org.w3c.*', 'org.xml.sax*', 'sun.*', 'sunw.*', 'com.sun.*',
            'groovy.*', 'org.codehaus.groovy.*'
    ]

    String getPackageFilter() {
        this.packageFilter.join(';')
    }


}
