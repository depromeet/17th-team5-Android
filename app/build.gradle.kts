import java.util.Properties

plugins {
    alias(libs.plugins.depromeet.team5.application)
    alias(libs.plugins.depromeet.team5.application.compose)
    alias(libs.plugins.depromeet.team5.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.kotlinx.serialization)
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.depromeet.team5"

    defaultConfig {
        applicationId = "com.depromeet.team5"
        versionCode = 2
        versionName = "0.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val kakaoNativeAppKey = properties["kakao_native_app_key"].toString()
        buildConfigField("String", "KAKAO_NATIVE_APP_KEY", "\"$kakaoNativeAppKey\"")
        manifestPlaceholders["KAKAO_NATIVE_APP_KEY"] = kakaoNativeAppKey
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
            resValue("string", "app_name", "Hedge.dev")
            applicationIdSuffix = ".dev"
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }

        release {
            resValue("string", "app_name", "Hedge")
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

    buildFeatures{
        buildConfig = true
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
    implementation(projects.features.retrospect)
    implementation(projects.features.home)
    implementation(projects.features.feedback)
    implementation(projects.features.reasons)
    implementation(projects.features.principlegroupdetail)
    implementation(projects.features.principlemodification)
    implementation(projects.features.principlegroupmodification)
    implementation(projects.features.login)

    implementation(libs.androidx.core.ktx)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kakao.user)
    implementation(libs.androidx.datastore.preferences)
}
