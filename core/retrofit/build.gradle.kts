plugins {
    alias(libs.plugins.depromeet.team5.library)
    alias(libs.plugins.depromeet.team5.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.depromeet.team5.core.retrofit"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(projects.depromeet.core.remotedatasource)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.com.squareup.retrofit2.retrofit)
    implementation(libs.com.jakewharton.retrofit.retrofit2.kotlinx.serialization.converter)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.com.squareup.okhttp3.okhttp)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)
}