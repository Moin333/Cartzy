plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
    id ("org.jetbrains.kotlin.plugin.compose")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
    id("io.realm.kotlin")
}



android {
    namespace = "com.example.cartzyapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.cartzyapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Realm
    implementation(libs.library.base)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)

    // Hilt dependencies
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)

    // Hilt with Compose
    implementation (libs.androidx.hilt.navigation.compose)

    // If using coroutines, include this

    kapt (libs.androidx.hilt.compiler)

    // Coil
    implementation(libs.coil.compose)

    implementation (libs.androidx.activity.ktx) // Make sure to use the latest version
    implementation (libs.androidx.fragment.ktx)  // Make sure to use the latest version


    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}