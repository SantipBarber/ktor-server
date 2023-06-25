val ktor_version: String = "2.3.1"
val kotlin_version: String = "1.8.22"
val logback_version: String = "1.4.7"

plugins {
    kotlin("jvm") version "1.8.22"
    id("io.ktor.plugin") version "2.3.1"
    kotlin("plugin.serialization") version "1.8.22"
    id("com.squareup.sqldelight") version "1.5.4"
}

group = "com.spbarber"
version = "0.0.1"
application {
    mainClass.set("com.spbarber.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
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


    implementation("ch.qos.logback:logback-classic:$logback_version")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    implementation("com.squareup.sqldelight:sqlite-driver:1.5.4")

}