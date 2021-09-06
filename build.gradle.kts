// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }

    dependencies {
        classpath(libs.gradle.android)
        classpath(libs.gradle.kotlin)
        classpath(libs.gradle.kotlinSerialization)
        classpath(libs.easyLauncher)
        classpath(libs.googleServices)
        classpath(libs.gradle.firebase)
    }
}

tasks.register("clean").configure {
    delete("build")
}