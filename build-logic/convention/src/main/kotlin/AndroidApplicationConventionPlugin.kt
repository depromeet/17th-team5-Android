import com.android.build.api.dsl.ApplicationExtension
import com.depromeet.team5.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project


class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure(ApplicationExtension::class.java) {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35
            }
        }
    }
}
