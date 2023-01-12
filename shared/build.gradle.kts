plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("kotlin-parcelize")
}

kotlin {
    android()

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:0.15.0")
            export("dev.icerock.moko:mvvm-livedata:0.15.0")
            export("dev.icerock.moko:mvvm-livedata-resources:0.15.0")
            export("dev.icerock.moko:mvvm-state:0.15.0")
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {

        /*
        implementation(Libraries.Common.sqlDelight)
                implementation(Libraries.Common.sqlDelightExtension)
                implementation(Libraries.Common.kotlinxSerializationCore)
                implementation(Libraries.Common.kotlinxCoroutinesCore)
         */
        val commonMain by getting {
            dependencies {

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

                implementation("com.squareup.sqldelight:runtime:1.5.3")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.4")

                val napier = "2.6.1"
                implementation("io.github.aakira:napier:$napier")

                val koin = "3.2.2"
                implementation("io.insert-koin:koin-core:$koin")

                val moko = "0.15.0"
                implementation("dev.icerock.moko:mvvm-core:$moko") // only ViewModel, EventsDispatcher, Dispatchers.UI
                implementation("dev.icerock.moko:mvvm-flow:$moko") // api mvvm-core, CFlow for native and binding extensions
                implementation("dev.icerock.moko:mvvm-livedata:$moko") // api mvvm-core, LiveData and extensions
                implementation("dev.icerock.moko:mvvm-state:$moko") // api mvvm-livedata, ResourceState class and extensions
                implementation("dev.icerock.moko:mvvm-livedata-resources:$moko") // api mvvm-core, moko-resources, extensions for LiveData with moko-resources
                implementation("dev.icerock.moko:mvvm-flow-resources:$moko") // api mvvm-core, moko-resources, extensions for Flow with moko-resources
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:android-driver:1.5.3")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {

            dependencies {
                implementation("com.squareup.sqldelight:native-driver:1.5.3")
            }

            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

sqldelight {
    database("TaskDatabase") {
        packageName = "com.android.todozen"
        sourceFolders = listOf("sqldelight")
    }
}

android {
    namespace = "com.android.todozen"
    compileSdk = 32
    defaultConfig {
        minSdk = 24
        targetSdk = 32
    }
}