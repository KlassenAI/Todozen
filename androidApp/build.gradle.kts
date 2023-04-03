plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.android.todozen"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.android.todozen"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":shared"))

    // Architecture
    val moko = "0.15.0"
    implementation("dev.icerock.moko:mvvm-core:$moko") // only ViewModel, EventsDispatcher, Dispatchers.UI
    implementation("dev.icerock.moko:mvvm-flow:$moko") // api mvvm-core, CFlow for native and binding extensions
    implementation("dev.icerock.moko:mvvm-livedata:$moko") // api mvvm-core, LiveData and extensions
    implementation("dev.icerock.moko:mvvm-state:$moko") // api mvvm-livedata, ResourceState class and extensions
    implementation("dev.icerock.moko:mvvm-livedata-resources:$moko") // api mvvm-core, moko-resources, extensions for LiveData with moko-resources
    implementation("dev.icerock.moko:mvvm-flow-resources:$moko") // api mvvm-core, moko-resources, extensions for Flow with moko-resources

    // UI
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.kirich1409:viewbindingpropertydelegate:1.5.6")
    val adapterDelegate = "4.3.2"
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl:$adapterDelegate")
    implementation("com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:$adapterDelegate")
    implementation("com.github.QuadFlask:colorpicker:0.0.15")
    implementation("com.github.skydoves:powermenu:2.2.2")

    // Navigation
    val nav = "2.5.3"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav")
    implementation("androidx.navigation:navigation-ui-ktx:$nav")

    // Dependency Injection
    val koinAndroid = "3.2.3"
    implementation("io.insert-koin:koin-android:$koinAndroid")
    implementation("io.insert-koin:koin-android-compat:$koinAndroid")
    implementation("io.insert-koin:koin-androidx-workmanager:$koinAndroid")
    implementation("io.insert-koin:koin-androidx-navigation:$koinAndroid")

    // Date and Time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("com.kizitonwose.calendar:view:2.0.0")

    // Core Library Desugaring (for datetime)
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")

    // Asynchronous
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    val swiper = "1.1.0"
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:$swiper")
}