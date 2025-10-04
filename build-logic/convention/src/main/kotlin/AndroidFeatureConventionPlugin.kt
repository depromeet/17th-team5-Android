import com.android.build.gradle.LibraryExtension
import com.depromeet.team5.androidTestImplementation
import com.depromeet.team5.configureAndroidCompose
import com.depromeet.team5.configureKotlinAndroid
import com.depromeet.team5.implementation
import com.depromeet.team5.libs
import com.depromeet.team5.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(target.pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("org.jetbrains.kotlin.plugin.serialization")
        }

        extensions.configure(LibraryExtension::class.java) {
            configureKotlinAndroid(this)
            configureAndroidCompose(this)
            defaultConfig.targetSdk = 35
        }

        dependencies {
            implementation(libs.findLibrary("androidx-core-ktx").get())
            implementation(libs.findLibrary("kotlinx-coroutines-core").get())

            implementation(
                libs.findLibrary("org-jetbrains-kotlinx-kotlinx-serialization-json").get()
            )

            testImplementation(libs.findLibrary("junit").get())
            androidTestImplementation(libs.findLibrary("androidx-junit").get())
            androidTestImplementation(libs.findLibrary("androidx-espresso-core").get())
        }
    }
}
