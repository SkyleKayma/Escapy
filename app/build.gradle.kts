plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
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
        getByName("release") {
            isMinifyEnabled = false
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