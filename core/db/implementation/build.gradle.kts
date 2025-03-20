plugins {
    alias(libs.plugins.io.github.mew22.becreative.android.lib.plugin)
}

dependencies {
    implementation(projects.core.db.gateway)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
}