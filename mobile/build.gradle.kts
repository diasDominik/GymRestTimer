plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "de.dominikdias.gymresttimer"
    compileSdk = 34

    defaultConfig {
        applicationId = "de.dominikdias.gymresttimer"
        minSdk = 26
        targetSdk = 34
        versionCode = 2
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.compose = true

    kotlin.jvmToolchain(libs.versions.java.jdk.get().toInt())

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.play.services.wearable)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.collections.immutable)
    implementation(project(":database"))
    implementation(project(":extensions"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    wearApp(project(":wear"))
}