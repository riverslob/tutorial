plugins {
    id("java-common-convention")
    id("org.springframework.boot").version("2.4.3")
    id("io.spring.dependency-management").version("1.0.11.RELEASE")
}

dependencies {
//    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")

//    implementation("com.google.guava:guava:30.1.1-jre")

//    implementation("ognl:ognl:3.2.21")
//    implementation("org.projectlombok:lombok:1.18.20")
//    annotationProcessor("org.projectlombok:lombok:1.18.20")

    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

//    implementation("org.hibernate:hibernate-validator:6.0.13.Final")
//    implementation("com.github.ben-manes.caffeine:caffeine:3.0.3")
//    implementation("org.apache.commons:commons-lang3:3.12.0")
//    implementation("commons-beanutils:commons-beanutils:1.9.4")
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.3")
//    implementation("org.springframework.boot:spring-boot-starter-cache")
//    implementation('org.springframework.boot:spring-boot-starter-validator'
//    implementation("org.springframework.data:spring-data-redis:2.5.2")
//    implementation("org.springframework.data:spring-data-redis")
//    implementation("org.springframework.boot:spring-boot-starter-data-redis") {
//        exclude("io.lettuce", "lettuce-core")
//    }

//    implementation("redis.clients:jedis:3.6.1")
//    implementation("org.redisson:redisson-spring-boot-starter:3.13.6")

    implementation("com.h2database:h2")
//    implementation "io.projectreactor:reactor-core:3.4.6"
//    testImplementation "io.projectreactor:reactor-test:3.4.6")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
//    testImplementation "org.spockframework:spock-core:$spockVersion"
//    testImplementation "org.spockframework:spock-spring:$spockVersion"
    testImplementation("cglib:cglib-nodep:3.2.5")
    testImplementation("org.mockito:mockito-junit-jupiter:3.9.0")
    testImplementation("com.tngtech.archunit:archunit-junit5:0.21.0")
}


