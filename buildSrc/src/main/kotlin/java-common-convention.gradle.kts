plugins {
    java
}

group = "me.test.tutorial"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    constraints {
        implementation("org.apache.commons:commons-text:1.9")
    }

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
}

tasks.test {
    useJUnitPlatform()
}
