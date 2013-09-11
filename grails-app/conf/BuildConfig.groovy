grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    legacyResolve false // whether to do a secondary resolve on plugin installation, not advised and here for backwards compatibility
    repositories {
        grailsCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.21'
    }

    plugins {
        build(":tomcat:$grailsVersion",
                ":release:2.2.0",
                ":rest-client-builder:1.0.3") {
            export = false
        }
        compile(':bean-fields:1.0') {
            export = false
        }
        compile(':blueprint:1.0.2') {
            export = false
        }
        compile(':cache-headers:1.1.5') {
            export = false
        }
        compile(':feeds:1.5') {
            export = false
        }
        runtime ":hibernate:$grailsVersion"
        runtime ":tomcat:$grailsVersion"
        compile(':jquery:1.8.0') {
            export = false
        }
        compile(':jquery-ui:1.8.24') {
            export = false
        }
        compile(':navigation:1.3.2') {
            export = false
        }
        compile(':quartz:1.0-RC2') {
            export = false
        }
        compile(':searchable:0.6.4') {
            export = false
        }
        compile(':taggable:1.0.1') {
            export = false
        }
        runtime(":cors:1.1.0") {
            export = false
        }
    }
}
grails.plugin.location.'MandrillGrailsPlugin' = "../mandrill"
grails.plugin.location.'WeceemGrailsPlugin' = "../weceem-plugin"
grails.plugin.location.'ConfSpeakersWeceemPlugin' = "../ConfSpeakersWeceemPlugin"
