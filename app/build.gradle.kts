plugins {
    alias(libs.plugins.depromeet.team5.application)
    alias(libs.plugins.depromeet.team5.application.compose)
    alias(libs.plugins.depromeet.team5.hilt)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.depromeet.team5"

    defaultConfig {
        applicationId = "com.depromeet.team5"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".dev"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.localdatasource)
    implementation(projects.core.remotedatasource)
    implementation(projects.core.retrofit)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.features.search)
    implementation(projects.features.principle)
    implementation(projects.features.retrospect)
    implementation(projects.features.home)
    implementation(projects.features.feedback)

    implementation(projects.features.reasons)

    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}