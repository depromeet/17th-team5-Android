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
    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.androidx.junit)
    testImplementation(libs.androidx.test.runner)
    testImplementation(libs.kotlinx.coroutine.test)

    testImplementation("org.robolectric:robolectric:4.13") // 최신 안정 버전 확인

    // AndroidX Test Core (ApplicationProvider 등을 JVM에서 사용하기 위해 필요)
    testImplementation("androidx.test:core-ktx:1.6.1")
    testImplementation("androidx.test.ext:junit-ktx:1.2.1")

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