plugins {
    alias(libs.plugins.io.github.mew22.becreative.kotlin.lib.plugin)
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.room.common)
    ksp(libs.room.compiler)

    implementation(projects.feature.mission.data)
    implementation(projects.feature.mission.domain)
    implementation(projects.feature.feed.domain)
}