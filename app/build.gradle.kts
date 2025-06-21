plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "lk.sugaapps.smartharvest"
    compileSdk = 35
    buildFeatures {
        viewBinding = true
        // Enable the feature
        buildConfig = true
    }
    defaultConfig {
        applicationId = "lk.sugaapps.smartharvest"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "WEATHER_API_KEY", "\"bd0e2d075e8946aa893165613251806\"")
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation (libs.lottie)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)

    implementation (libs.rxandroid)
    implementation (libs.rxjava)


    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    implementation (libs.hilt.android)
    implementation(libs.work.runtime)
    annotationProcessor (libs.hilt.compiler)

    // For instrumentation tests
    androidTestImplementation  (libs.hilt.android.testing)
    androidTestAnnotationProcessor (libs.hilt.compiler)

    // For local unit tests
    testImplementation (libs.hilt.android.testing)
    testAnnotationProcessor (libs.hilt.compiler)
    implementation (libs.glide)


    implementation (libs.mpandroidchart)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation (libs.retrofit2.converter.gson)


    // ViewModel and LiveData
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.hilt.work)
    implementation(libs.pdf.viewer)
    implementation (libs.jsoup)
    implementation( libs.converter.scalars)


}