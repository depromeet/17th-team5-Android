import com.depromeet.team5.implementation
import com.depromeet.team5.ksp
import com.depromeet.team5.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class PureHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.google.devtools.ksp")
        }

        dependencies {
            implementation(libs.findLibrary("hilt-core").get())
            ksp(libs.findLibrary("hilt-compiler").get())
        }
    }
}
