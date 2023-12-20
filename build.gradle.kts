plugins {
    id("java")
    id("jacoco")
}

group = "com.ise.taxiapp"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "com.ise.taxiapp.cli.CliDriver"
}

tasks.test {
    useJUnitPlatform()
}
jacocoTestReport {
    reports {
        xml.enabled true
        html.enabled enabled
    }
}

check.dependsOn jacocoTestReport
