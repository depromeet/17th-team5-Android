plugins {
    alias(libs.plugins.depromeet.team5.library)
    alias(libs.plugins.kotlinx.serialization)
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

    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
    implementation(libs.androidx.compose.runtime.annotation)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}
