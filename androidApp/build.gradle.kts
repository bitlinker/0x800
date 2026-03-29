import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    dependencies {
        implementation(libs.androidx.appcompat)
        implementation(libs.androidx.activityCompose)
        implementation(libs.compose.uitooling)
        implementation(libs.kotlinx.coroutines.android)
        implementation(libs.napier)
        implementation(libs.essenty.stateKeeper)
        implementation(libs.multiplatformSettings.core)
        implementation(projects.composeApp)
    }

    target {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}

private fun versionToCode(version: String): Int {
    return version
        .split('.')
        .map { it.toInt() }
        .fold(0) { acc, cur ->
            acc * 100 + cur
        }
}

android {
    namespace = libs.versions.appGroup.get()
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        applicationId = "${libs.versions.appGroup.get()}.androidApp"
        versionCode = versionToCode(libs.versions.appVersion.get())
        versionName = libs.versions.appVersion.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    buildTypes {
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
            )
        }
        debug {
            isDebuggable = true
        }
    }
}
