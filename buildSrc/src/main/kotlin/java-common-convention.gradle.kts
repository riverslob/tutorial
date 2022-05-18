plugins {
    java
}

group = "me.test.tutorial"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

//tasks.withType<JavaCompile> {
//}

tasks.compileJava {
    options.isIncremental = true
    options.isFork = true
    options.isFailOnError = false
//    sourceCompatibility = "1.8"
//    targetCompatibility = "1.8"
//    options.encoding = "UTF-8"
}

dependencies {
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    constraints {
        implementation("org.apache.commons:commons-text:1.9")
    }

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.mockito:mockito-junit-jupiter:3.9.0")
}

tasks.test {
    useJUnitPlatform()
}
