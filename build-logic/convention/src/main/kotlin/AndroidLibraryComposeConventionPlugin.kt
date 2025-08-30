import com.android.build.api.dsl.LibraryExtension
import com.depromeet.team5.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val extension = extensions.getByType<LibraryExtension>()
        configureAndroidCompose(extension)
    }
}