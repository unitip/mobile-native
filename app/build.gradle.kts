import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

//    id("kotlin-kapt")
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.unitip.mobile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.unitip.mobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            val properties = Properties()
            properties.load(rootProject.file(".env.local").inputStream())

            buildConfigField(
                "String",
                "API_BASE_URL",
                "\"${properties.getProperty("API_BASE_URL")}\""
            )

            buildConfigField(
                "String",
                "MQTT_SECRET",
                "\"${properties.getProperty("MQTT_SECRET")}\""
            )
            buildConfigField(
                "String",
                "MQTT_SERVER_URI",
                "\"${properties.getProperty("MQTT_SERVER_URI")}\""
            )
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")

            val properties = Properties()
            properties.load(rootProject.file(".env.production").inputStream())

            buildConfigField(
                "String",
                "API_BASE_URL",
                "\"${properties.getProperty("API_BASE_URL")}\""
            )

            buildConfigField(
                "String",
                "MQTT_SECRET",
                "\"${properties.getProperty("MQTT_SECRET")}\""
            )
            buildConfigField(
                "String",
                "MQTT_SERVER_URI",
                "\"${properties.getProperty("MQTT_SERVER_URI")}\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // compose navigation
    implementation(libs.androidx.navigation.compose)

    // json serialization
    implementation(libs.kotlinx.serialization.json)

    // hilt dagger
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.ui.text.google.fonts)
    ksp(libs.hilt.android.compiler)

    // lucide icons
    implementation(libs.icons.lucide)

    // mqtt client
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.github.hannesa2:paho.mqtt.android:4.3")
//    implementation(libs.org.eclipse.paho.client.mqttv3)
//    implementation(libs.org.eclipse.paho.android.service)

    // retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // functional programming
    implementation(libs.arrow.core.jvm)

    // open street map
    implementation("org.osmdroid:osmdroid-android:6.1.20")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

//kapt {
//    correctErrorTypes = true
//}