plugins {
    alias(libs.plugins.io.github.mew22.becreative.compose.lib.plugin)
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.monitoring.gateway)
}