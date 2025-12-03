import com.android.build.gradle.LibraryExtension
import com.depromeet.team5.configureKotlinAndroid
import com.depromeet.team5.implementation
import com.depromeet.team5.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        with(pluginManager) {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        extensions.configure(LibraryExtension::class.java) {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk = 35
            defaultConfig.consumerProguardFile("consumer-rules.pro")
        }

        dependencies {
            implementation(libs.findLibrary("androidx-core-ktx").get())
            implementation(libs.findLibrary("kotlinx-coroutines-core").get())
        }
    }
}
