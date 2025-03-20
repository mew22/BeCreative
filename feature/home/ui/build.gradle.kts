plugins {
    alias(libs.plugins.io.github.mew22.becreative.compose.lib.plugin)
}

dependencies {

    implementation(projects.core.ui)
    implementation(projects.core.monitoring.gateway)

    testImplementation(testFixtures(projects.core.common))
}