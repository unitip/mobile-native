import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

//    id("kotlin-kapt")
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.openapi.generator") version "7.10.0"
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
                "BASE_URL",
                "\"${properties.getProperty("BASE_URL")}\""
            )
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
                "BASE_URL",
                "\"${properties.getProperty("BASE_URL")}\""
            )
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
    implementation(libs.converter.scalars)


    // functional programming
    implementation(libs.arrow.core.jvm)

    // open street map
    implementation("org.osmdroid:osmdroid-android:6.1.20")

    // google play service
    implementation("com.google.android.gms:play-services-location:21.3.0")

    // shimmer
    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.2")

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

tasks.register("downloadSwagger") {
    doLast {
        val properties = Properties()
        properties.load(rootProject.file(".env.local").inputStream())
        val localBaseUrl = properties.getProperty("BASE_URL")

//        val swaggerUrl = "${localBaseUrl}api/v1/docs/swagger.json"
        val swaggerUrl = "http://localhost:3000/api/v1/docs/swagger.json"
        val outputDir = "$rootDir/swagger.json"

        ant.withGroovyBuilder {
            "get"(
                "src" to swaggerUrl,
                "dest" to outputDir,
                "verbose" to "true"
            )
        }
    }
}

openApiGenerate {
    generatorName.set("kotlin")
    skipValidateSpec.set(true)
    library.set("jvm-retrofit2")
    packageName.set("com.unitip.mobile.network.openapi")
    generateApiTests.set(false)
    generateModelTests.set(false)
    inputSpec.set("$rootDir/swagger.json")
    configOptions.set(
        mapOf(
            "serializationLibrary" to "gson",
            "useCoroutines" to "true",
        )
    )
}

kotlin {
    sourceSets {
        main {
            kotlin.srcDir("${layout.buildDirectory.get()}/generate-resources/main/src")
        }
    }
}

tasks.register("codegen") {
    dependsOn(
        "downloadSwagger",
        "openApiGenerate"
    )
}

tasks.withType<KotlinCompile>().configureEach {
    dependsOn(
        "downloadSwagger",
        "openApiGenerate"
    )
}