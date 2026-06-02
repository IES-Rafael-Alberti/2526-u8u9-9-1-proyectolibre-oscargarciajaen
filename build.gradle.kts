plugins {
    kotlin("jvm") version "2.3.0"
    id("org.jetbrains.dokka") version "2.0.0"
}

group = "org.iesra"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.13")
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.h2database:h2:2.2.224")
    implementation("org.mongodb:mongodb-driver-sync:5.1.0")
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    outputDirectory.set(file("documentacion"))
}