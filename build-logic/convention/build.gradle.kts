import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "io.github.mew22.becreative.build_logic.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_21.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    implementation(libs.plugin.detekt)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    // workaround to allow precompiled script to access version catalog
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("kotlinLib") {
            id = "io.github.mew22.becreative.kotlin-lib-plugin"
            implementationClass = "KotlinLibPlugin"
        }
        register("androidLib") {
            id = "io.github.mew22.becreative.android-lib-plugin"
            implementationClass = "AndroidLibPlugin"
        }
        register("compose") {
            id = "io.github.mew22.becreative.compose-plugin"
            implementationClass = "ComposePlugin"
        }
        register("composeLib") {
            id = "io.github.mew22.becreative.compose-lib-plugin"
            implementationClass = "ComposeLibPlugin"
        }
        register("composeApp") {
            id = "io.github.mew22.becreative.compose-app-plugin"
            implementationClass = "ComposeAppPlugin"
        }
        register("app") {
            id = "io.github.mew22.becreative.app-plugin"
            implementationClass = "AppPlugin"
        }
        register("detekt") {
            id = "io.github.mew22.becreative.detekt-plugin"
            implementationClass = "DetektPlugin"
        }
    }
}
