import java.util.Properties

plugins {
    alias(libs.plugins.depromeet.team5.library)
    alias(libs.plugins.depromeet.team5.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.depromeet.team5.core.retrofit"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", properties["base_url"].toString())
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", properties["base_url"].toString())
        }
    }

    hilt {
        enableAggregatingTask = true
    }

    buildFeatures {
        buildConfig = true
    }

}

dependencies {
    implementation(projects.depromeet.core.remotedatasource)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.gson)
    implementation(libs.converter.gson)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.com.squareup.retrofit2.retrofit)
    implementation(libs.com.jakewharton.retrofit.retrofit2.kotlinx.serialization.converter)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.com.squareup.okhttp3.okhttp)
    implementation(libs.com.squareup.okhttp3.logging.interceptor)
}