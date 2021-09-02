dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io") }
        jcenter() // Warning: this repository is going to shut down soon
    }
}

enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "Escapy"
include(":app", ":data")