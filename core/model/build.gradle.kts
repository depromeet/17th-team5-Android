plugins {
    alias(libs.plugins.depromeet.team5.library)
}

android {
    namespace = "com.depromeet.team5.core.model"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

        }
    }
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.androidx.compose.runtime.annotation)
}
