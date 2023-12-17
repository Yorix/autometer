plugins {
    `java-library`
    `maven-publish`
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"

}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    api(libs.jakarta.validation.jakarta.validation.api)
    api(libs.org.springframework.boot.spring.boot.starter.data.jpa)
    api(libs.org.springframework.boot.spring.boot.starter.web)
    api(libs.org.springframework.boot.spring.boot.starter.security)
    api(libs.org.thymeleaf.extras.thymeleaf.extras.springsecurity6)
    api(libs.org.springframework.boot.spring.boot.starter.thymeleaf)
    api(libs.mysql.mysql.connector.java)
    api(libs.org.springframework.boot.spring.boot.starter.mail)
    api(libs.org.json.json)
    testImplementation(libs.org.springframework.boot.spring.boot.starter.test)
    testImplementation(libs.org.springframework.security.spring.security.test)
    compileOnly(libs.org.projectlombok.lombok)
    annotationProcessor(libs.org.projectlombok.lombok)
}

group = "com.yorix"
version = "2.0"
description = "AutoMeter"
java.sourceCompatibility = JavaVersion.VERSION_17

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "com.yorix.autometer.AutometerApplication"
    }
}

project.setProperty("mainClassName", "com.yorix.autometer.AutometerApplication")