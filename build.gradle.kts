apply("gradle/native-libs.gradle.kts")

plugins {
    kotlin("multiplatform") version "1.9.0"
    id("org.jmailen.kotlinter") version "3.12.0"
}

group = "io.github.tscholze.kblink"
version = "0.1-SNAPSHOT"
val ktpgioVersion = "0.0.9"
val ktorVersion = "2.3.5"

repositories {
    mavenCentral()
}

kotlin {
    // 64bit arm target
    val native = linuxArm64("native")

    sourceSets {
        val nativeMain by getting {
            dependencies {
                // ktgp
                implementation("io.ktgp:core:$ktpgioVersion")

                // Ktor
                implementation("io.ktor:ktor-server-core:$ktorVersion")
                implementation("io.ktor:ktor-server-cio:$ktorVersion")

                // Default C
                implementation(kotlin("stdlib"))
            }
        }
    }
    configure(listOf(native)) {
        val libs = "$buildDir/native/libs/usr/lib/aarch64-linux-gnu/"

        binaries.executable()
        binaries.all {
            linkTask.dependsOn(tasks.getByPath(":nativeLibs"))
            linkerOpts.add("-L$libs")
        }
    }
}