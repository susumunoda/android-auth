@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("multiplatform")
    `maven-publish`
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")

        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    ios()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines)
            }
        }
    }
}

android {
    namespace = "com.susumunoda.android.auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

group = "com.susumunoda.auth"
version = "1.0"
val artifactIdOverride = "authcontroller"

publishing {
    publications.withType<MavenPublication> {
        val publication = this
        afterEvaluate {
            val project = this
            // In a single Gradle project setup, `artifactId` would default to the root project
            // name, which would be reasonable to change. However, when the library is a module
            // within a top-level project, `artifactId` defaults to the module's name (e.g. `lib`
            // or `library`), which ideally we would not have to change. Therefore, we override it
            // here to be the desired value, including the target-specific suffixes.
            // See https://docs.gradle.org/current/userguide/publishing_maven.html#publishing_maven:deferred_configuration
            artifactId = if (publication.name == "kotlinMultiplatform") {
                artifactIdOverride
            } else {
                publication.artifactId.replaceFirst(project.name, artifactIdOverride)
            }
        }
    }
}
