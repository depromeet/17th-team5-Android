plugins {
    alias(libs.plugins.depromeet.team5.library)
    alias(libs.plugins.depromeet.team5.library.compose)
    alias(libs.plugins.depromeet.team5.hilt)
}

android {
    namespace = "com.depromeet.teem5.features.home"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    hilt{
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(projects.core.designsystem)

    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}