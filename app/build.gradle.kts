import java.util.Properties

plugins {
    alias(libs.plugins.depromeet.team5.application)
    alias(libs.plugins.depromeet.team5.application.compose)
    alias(libs.plugins.depromeet.team5.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "com.depromeet.team5"

    defaultConfig {
        applicationId = "com.depromeet.team5"
        versionCode = 2
        versionName = "0.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("keystore.properties")
            if (keystorePropertiesFile.exists()) {
                val properties = Properties()
                properties.load(keystorePropertiesFile.inputStream())
                keyAlias = properties.getProperty("keyAlias")
                keyPassword = properties.getProperty("keyPassword")
                storeFile = file(properties.getProperty("storeFile"))
                storePassword = properties.getProperty("storePassword")
            }
        }
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
            signingConfig = signingConfigs.getByName("release")

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
    implementation(projects.core.navigation)
    implementation(projects.core.logger)
    implementation(projects.core.ui)
    implementation(projects.features.search)
    implementation(projects.features.principle)
    implementation(projects.features.retrospect)
    implementation(projects.features.home)
    implementation(projects.features.feedback)
    implementation(projects.features.reasons)
    implementation(projects.features.principledetail)

    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
