import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

apply(plugin = "com.github.ben-manes.versions")

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
    }

    dependencies {
        classpath(libs.pluginGradle.android)
        classpath(libs.pluginGradle.kotlin)
        classpath(libs.pluginGradle.kotlinSerialization)
        classpath(libs.pluginGradle.safeArgs)
        classpath(libs.pluginGradle.appDistribution)
        classpath(libs.pluginGradle.versions)
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}

subprojects {
    tasks {
        withType(JavaCompile::class.java).configureEach {
            sourceCompatibility = JavaVersion.VERSION_1_8.name
            targetCompatibility = JavaVersion.VERSION_1_8.name
        }

        withType(KotlinCompile::class.java).configureEach {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs += listOf("-Xopt-in=kotlin.RequiresOptIn")
            }
        }
    }
}