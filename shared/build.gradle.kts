plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    id("kotlin-parcelize")
}

kotlin {
    android()

    val moko = "0.15.0"
    val sqldelight = "1.5.3"

    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
            export("dev.icerock.moko:mvvm-core:$moko")
            export("dev.icerock.moko:mvvm-livedata:$moko")
            export("dev.icerock.moko:mvvm-livedata-resources:$moko")
            export("dev.icerock.moko:mvvm-state:$moko")
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

        val commonMain by getting {
            dependencies {

                val sqlCoroutines = "1.5.4"
                val datetime = "0.4.0"
                val coroutines = "1.6.4"
                val kermit = "1.2.2"
                val koin = "3.2.2"

                implementation(kotlin("stdlib-common"))

                // Storage
                implementation("com.squareup.sqldelight:runtime:$sqldelight")
                implementation("com.squareup.sqldelight:coroutines-extensions:$sqlCoroutines")

                // Date and Time
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetime")

                // Asynchronous
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines")

                // Log
                implementation("co.touchlab:kermit:$kermit")

                // Dependency Injection
                implementation("io.insert-koin:koin-core:$koin")

                // Architecture
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
                implementation("com.squareup.sqldelight:android-driver:$sqldelight")
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {

            dependencies {
                implementation("com.squareup.sqldelight:native-driver:$sqldelight")
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