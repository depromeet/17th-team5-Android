import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        pluginManager.withPlugin("org.jetbrains.kotlin.android") {
            pluginManager.apply("com.google.devtools.ksp")
        }
        pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
            pluginManager.apply("com.google.devtools.ksp")
        }

        pluginManager.withPlugin("com.android.base") {
            pluginManager.apply("com.google.dagger.hilt.android")

            dependencies {
                add("implementation", libs.findLibrary("hilt-android").get())
                add("ksp", libs.findLibrary("hilt-android-compiler").get())
            }
        }
    }

}