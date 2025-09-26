plugins {
    alias(libs.plugins.depromeet.team5.library)
    alias(libs.plugins.depromeet.team5.library.compose)
    alias(libs.plugins.depromeet.team5.hilt)
}

android {
    namespace = "com.depromeet.team5.features.retrospect"

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

    implementation(libs.material)
    implementation(projects.core.model)

    //버전카탈로그 파일에서 충돌나지 않기 위해 일단 여기서 선언
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}