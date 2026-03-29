@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN

group = libs.versions.appGroup.get()

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.multiplatform.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildKonfig)
}

kotlin {
    android {
        namespace = "${libs.versions.appGroup.get()}.common"
        compileSdk = libs.versions.android.compileSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }

        androidResources {
            enable = true
        }
    }

    jvm()

    wasmJs {
        outputModuleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static(project.projectDir.path)
                }
            }
        }
        binaries.executable()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.components.resources)
            implementation(libs.napier)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.multiplatformSettings.core)
            implementation(libs.multiplatformSettings.coroutines)
            implementation(libs.multiplatformSettings.makeObservable)
            implementation(libs.essenty.stateKeeper)
        }

        jvmMain.dependencies {
            implementation(libs.compose.desktop)
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }

        wasmJsMain.dependencies {
        }

        iosMain.dependencies {
        }
    }

    compilerOptions {
        optIn.add("com.russhwolf.settings.ExperimentalSettingsApi")
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "${libs.versions.appGroup.get()}.desktopApp"
            packageVersion = libs.versions.appVersion.get()
        }
    }
}

buildkonfig {
    packageName = libs.versions.appGroup.get()

    defaultConfigs {
        buildConfigField(
            type = STRING,
            name = "version",
            value = libs.versions.appVersion.get()
        )
        buildConfigField(
            type = STRING,
            name = "github",
            value = "https://github.com/bitlinker/0x800"
        )
        buildConfigField(
            type = STRING,
            name = "privacyPolicyUrl",
            value = "https://github.com/bitlinker/0x800/blob/main/privacy_policy.md"
        )

        val isDebug: String by project.extra
        buildConfigField(
            type = BOOLEAN,
            name = "isDebug",
            value = isDebug,
        )
    }
}