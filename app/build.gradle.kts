import org.gradle.language.nativeplatform.internal.BuildType
import java.text.SimpleDateFormat
import java.util.TimeZone
import java.util.Date

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("com.starter.easylauncher")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "fr.skyle.escapy"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = false

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters += setOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64")
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
            isMinifyEnabled = false

            versionNameSuffix = "-${buildTime()}"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
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
    implementation(libs.androidx.compose)
    implementation(libs.androidx.navigation)
    implementation(libs.bundles.androidx.lifecycle)
    implementation(libs.bundles.androidx.compose)

    // Android
    implementation(libs.android.material)

    // Serialization
    implementation(libs.kotlin.serialization)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // Timber
    implementation(libs.timber)

    // Koin
    implementation(libs.bundles.koin)

    // Coil
    implementation(libs.coil)

    // Accompanist
    implementation(libs.bundles.accompanist)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Tests
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.androidx.junit)
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.compose.ui.junit)
    androidTestImplementation(libs.test.compose.ui.tooling)
}