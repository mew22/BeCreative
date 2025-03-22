plugins {
    alias(libs.plugins.io.github.mew22.becreative.compose.lib.plugin)
}

dependencies {
    implementation(projects.feature.feed.domain)
    api(projects.feature.feed.data)
}