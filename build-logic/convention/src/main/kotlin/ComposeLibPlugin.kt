import com.android.build.gradle.LibraryExtension
import io.github.mew22.becreative.build_logic.convention.configureComposeAndroid
import io.github.mew22.becreative.build_logic.convention.configureComposeCompiler
import io.github.mew22.becreative.build_logic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class ComposeLibPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(libs.plugins.io.github.mew22.becreative.android.lib.plugin.get().pluginId)
            apply(libs.plugins.io.github.mew22.becreative.compose.common.plugin.get().pluginId)
        }
        extensions.configure<LibraryExtension>(::configureComposeAndroid)
        extensions.configure<ComposeCompilerGradlePluginExtension>(::configureComposeCompiler)
    }
}