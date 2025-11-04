plugins {
    alias(libs.plugins.depromeet.team5.library)
    alias(libs.plugins.depromeet.team5.library.compose)
    alias(libs.plugins.depromeet.team5.hilt)
}

android {
    namespace = "com.depromeet.team5.core.logger"

    defaultConfig {

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
        }
    }

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(projects.depromeet.core.designsystem)

    implementation(libs.timber)
    implementation(libs.firebase.crashlytics)

    implementation(libs.androidx.ui)
}
