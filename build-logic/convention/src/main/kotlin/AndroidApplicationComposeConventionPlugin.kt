import com.android.build.api.dsl.ApplicationExtension
import com.depromeet.team5.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val extension = extensions.getByType<ApplicationExtension>()
        configureAndroidCompose(extension)
    }
}