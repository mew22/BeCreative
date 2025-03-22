plugins {
    alias(libs.plugins.io.github.mew22.becreative.kotlin.lib.plugin)
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.feature.mission.domain)

    implementation(libs.room.common)
    ksp(libs.room.compiler)
}