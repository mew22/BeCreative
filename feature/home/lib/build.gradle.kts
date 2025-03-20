plugins {
    alias(libs.plugins.io.github.mew22.becreative.compose.lib.plugin)
}

dependencies {
    implementation(projects.feature.home.ui)

    implementation(projects.core.monitoring.gateway)
    implementation(projects.core.ui)
}