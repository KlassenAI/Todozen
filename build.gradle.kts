buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { setUrl("https://maven.fabric.io/public") }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.3")
        classpath("dev.icerock.moko:resources-generator:0.20.1")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}