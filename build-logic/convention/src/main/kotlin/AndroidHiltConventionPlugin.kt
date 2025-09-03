import com.depromeet.team5.implementation
import com.depromeet.team5.ksp
import com.depromeet.team5.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.google.dagger.hilt.android")
            apply("com.google.devtools.ksp")
        }

        dependencies {
            implementation(libs.findLibrary("hilt-android"))
            ksp(libs.findLibrary("hilt-android-compiler").get())
        }
    }
}

