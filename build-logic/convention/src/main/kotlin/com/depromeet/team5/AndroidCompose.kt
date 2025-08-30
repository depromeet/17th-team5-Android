package com.depromeet.team5

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    val libs = findVersionCatalog()

    commonExtension.apply {
        buildFeatures.compose = true
    }

    dependencies {
        implementation(platform(libs.findLibrary("androidx-compose-bom").get()))
        implementation(libs.findBundle("compose"))
        debugImplementation(libs.findBundle("compose-preview"))
    }
}
