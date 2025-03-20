plugins {
    alias(libs.plugins.io.github.mew22.becreative.compose.app.plugin)
}

dependencies {
    implementation(libs.material)

    implementation(projects.core.monitoring.gateway)
    implementation(projects.core.monitoring.implementation)
    implementation(projects.core.ui)
    implementation(projects.core.db.gateway)
    implementation(projects.core.db.implementation)
    implementation(projects.core.common)

    implementation(projects.feature.home.lib)


    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
}