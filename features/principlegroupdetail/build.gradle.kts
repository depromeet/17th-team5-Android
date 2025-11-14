plugins {
    alias(libs.plugins.depromeet.team5.feature)
    alias(libs.plugins.depromeet.team5.hilt)
    alias(libs.plugins.kotlinx.serialization)
}


android {
    namespace = "com.depromeet.team5.features.principlegroupdetail"

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
    implementation(projects.core.navigation)
    implementation(projects.core.domain)
    implementation(projects.core.ui)

    implementation(libs.coil.compose)

    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
}