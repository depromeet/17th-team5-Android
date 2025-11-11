plugins {
    alias(libs.plugins.depromeet.team5.feature)
    alias(libs.plugins.depromeet.team5.hilt)
}

android {
    namespace = "com.depromeet.team5.features.login"

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
    implementation(projects.core.domain)

    implementation(projects.core.navigation)
    implementation(projects.core.ui)
    implementation(projects.core.domain)

    implementation(libs.coil.compose)
    implementation(libs.kakao.user)
}