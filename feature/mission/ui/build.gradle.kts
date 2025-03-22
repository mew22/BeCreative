plugins {
    alias(libs.plugins.io.github.mew22.becreative.compose.lib.plugin)
}

dependencies {

    implementation(projects.core.ui)
    implementation(projects.core.common)
    implementation(projects.core.monitoring.gateway)

    implementation(projects.feature.mission.domain)

    testImplementation(testFixtures(projects.core.common))

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.compose)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.accompanist.permissions)
}