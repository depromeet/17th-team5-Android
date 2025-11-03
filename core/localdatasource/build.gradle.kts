plugins {
    alias(libs.plugins.depromeet.team5.library)
    alias(libs.plugins.depromeet.team5.hilt)
}

android {
    namespace = "com.depromeet.team5.core.localdatasource"

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
    implementation(projects.depromeet.core.data)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

// Gradle 설정값 확인
tasks.register("printSdkVersions") {
    doLast {
        println("========================================")
        println("Module: ${project.name}")
        println(" ")

        // Android 관련 버전
        println("compileSdk Version: ${android.compileSdk}")
        println("targetSdk Version: ${android.defaultConfig.targetSdk}")
        println("minSdk Version: ${android.defaultConfig.minSdk}")
        println(" ")

        // Java 관련 버전
        println("Source Compatibility: ${android.compileOptions.sourceCompatibility}")
        println("Target Compatibility: ${android.compileOptions.targetCompatibility}")
        println(" ")

        // Kotlin 관련 버전
        println("JVM Target: ${android.kotlinOptions.jvmTarget}")

        println("========================================")
    }
}