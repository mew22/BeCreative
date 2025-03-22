plugins {
    alias(libs.plugins.io.github.mew22.becreative.compose.lib.plugin)
}

dependencies {
    implementation(projects.feature.home.ui)
    implementation(projects.feature.feed.domain)
    implementation(projects.feature.mission.domain)

    implementation(projects.core.monitoring.gateway)
    implementation(projects.core.ui)
}