// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    id("io.realm.kotlin") version "2.3.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
}

buildscript {
    dependencies {
        classpath (libs.hilt.android.gradle.plugin)
        classpath(libs.gradle.plugin)
    }
}

