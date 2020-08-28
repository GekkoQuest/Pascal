import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "me.toonbasic"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://maven.notfab.net/SpigotMC")

    maven("https://kotlin.bintray.com/kotlinx") {
        name = "KotlinX"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    compile("info.debatty:java-lsh:0.12")

    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

tasks {
    shadowJar {
        minimize()
        relocate("kotlin", "me.toonbasic.pascal.libs.kotlin")
        relocate("org.jetbrains", "me.toonbasic.pascal.libs.org.jetbrains")
        relocate("org.intellij", "me.toonbasic.pascal.libs.org.intellij")
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.4"
}