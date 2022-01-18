import org.gradle.language.nativeplatform.internal.BuildType
import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.appdistribution")
    id("com.starter.easylauncher")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "fr.skyle.escapy"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = false

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters += setOf("arm64-v8a", "arm64-v9a", "x86_64")
        }
    }

    buildTypes {
        named(BuildType.DEBUG.name).configure {
            isMinifyEnabled = false

            versionNameSuffix = "-debug-${buildTime()}"
            applicationIdSuffix = ".debug"
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        named(BuildType.RELEASE.name).configure {
            isMinifyEnabled = true

            versionNameSuffix = "-${buildTime()}"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,NOTICE,NOTICE.txt,LICENSE,LICENSE.txt,DEPENDENCIES,DEPENDENCIES.txt}"
        }
    }
}

easylauncher {
    buildTypes {
        register(BuildType.DEBUG.name).configure {
            filters(chromeLike(label = "debug", ribbonColor = "#DF0503"))
        }
    }
}

fun buildTime(): String? {
    val df = SimpleDateFormat("yyyyMMdd")
    df.timeZone = TimeZone.getTimeZone("UTC")
    return df.format(Date())
}

dependencies {
    // Includes
    implementation(project(":data"))

    // AndroidX
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.androidx.lifecycle)

    // Material
    implementation(libs.android.material)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Timber
    implementation(libs.timber)

    // Hilt
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    // Coil
    implementation(libs.coil)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Navigation
    implementation(libs.bundles.navigation)

    // Tests
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.espresso.core)
}