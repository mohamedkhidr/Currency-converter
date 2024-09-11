import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.khidrew.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        //load the values from .properties file
        val keystoreFile = project.rootProject.file("secrets.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val apiKey = properties.getProperty("API_KEY") ?: ""

        buildConfigField(
            type = "String",
            name = "API_KEY",
            value = apiKey
        )
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
    buildFeatures{
        buildConfig = true
    }


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Room
    implementation(libs.androidx.room.runtime)
    kapt (libs.room.compiler)

    // Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    kapt (libs.hilt.android.compiler)
    implementation(project(":domain"))
}