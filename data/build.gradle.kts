import org.gradle.language.nativeplatform.internal.BuildType

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 23
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        named(BuildType.RELEASE.name).configure {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    // AndroidX
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.androidx.datastore)

    // Android
    implementation(libs.android.material)

    // Kotlin Serialization
    implementation(libs.kotlin.serialization)

    // Timber
    implementation(libs.timber)

    // Room
    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    // Koin
    implementation(libs.bundles.koin)

    // Retrofit
    implementation(libs.bundles.retrofit)

    // OkHttp
    implementation(libs.bundles.okhttp)
}