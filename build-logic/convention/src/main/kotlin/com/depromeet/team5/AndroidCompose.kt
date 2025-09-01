package com.depromeet.team5

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {

    apply(plugin = "org.jetbrains.kotlin.plugin.compose")

    commonExtension.apply {
        buildFeatures.compose = true
    }

    dependencies {

        implementation(libs.findLibrary("androidx-lifecycle-runtime-ktx").get())

        implementation(platform(libs.findLibrary("androidx-compose-bom").get()))
        androidTestImplementation(platform(libs.findLibrary("androidx-compose-bom").get()))

        implementationBundle(libs, "compose")
        debugImplementationBundle(libs, "compose-debug")
        androidTestImplementationBundle(libs, "compose-androidTest")
    }
}
