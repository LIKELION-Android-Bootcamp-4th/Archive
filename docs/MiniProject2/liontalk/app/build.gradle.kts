plugins {
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    alias(libs.plugins.google.services)

}

android {
    namespace = "com.likelion.liontalk"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.likelion.liontalk"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    //mqtt 설정중
    packaging {
        resources {
            excludes += "/META-INF/INDEX.LIST"
            excludes += "/META-INF/io.netty.versions.properties"
        }
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

    //jetpack compose
    implementation(libs.compose.nav)
    implementation(libs.coroutines.core)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.lifecycle.viewmodel.ktx)

    //db
    implementation(libs.room.runtime)
    implementation(libs.datastore.preferences)
    implementation(libs.androidx.runtime.livedata)

    //mqtt
    implementation(libs.hivemq.mqtt.client)
    //기타
    implementation(libs.coil.compose)

    //retrofit
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.gson)
    implementation(libs.okhttp.logging)

    kapt(libs.room.compiler)

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-functions-ktx")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // kakao auth
    implementation("com.kakao.sdk:v2-user:2.20.3")

    // naver auth
    implementation("com.navercorp.nid:oauth:5.9.0")




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}