val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val postgresql_version: String by project
val exposed_version: String by project

plugins {
    application
    id("io.ktor.plugin") version "2.3.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.serialization") version "1.8.22"
    id("com.squareup.sqldelight") version "1.5.4"
}

group = "com.spbarber"
version = "0.0.1"

tasks.register("stage") {
    dependsOn("clean")
    dependsOn("build")
    dependsOn("installDist")
}

application {
     //mainClass.set("com.spbarber.ApplicationKt")
     //val isDevelopment: Boolean = project.ext.has("development")
     //applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")

    mainClass.set("io.ktor.server.netty.EngineMain")
}

ktor {
    fatJar {
        archiveFileName.set("sct-server-docker-app.jar")
    }
}

repositories {
    mavenCentral()
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.spbarber.kotlinexpert.database"
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("com.squareup.sqldelight:sqlite-driver:1.5.4")
    implementation("com.squareup.sqldelight:runtime:1.5.4")

    implementation("org.postgresql:postgresql:$postgresql_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
}