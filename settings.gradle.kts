rootProject.name = "BeCreative"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
includeBuild("build-logic")
include(":app")
include(
    ":core:common",
    ":core:ui",
    ":core:monitoring:gateway",
    ":core:monitoring:implementation",
    ":core:db:gateway",
    ":core:db:implementation",
)
include(
    ":feature:home:ui",
    ":feature:home:lib",
)
include(
    ":feature:feed:lib",
    ":feature:feed:domain",
    ":feature:feed:data",
)

include(
    ":feature:mission:lib",
    ":feature:mission:domain",
    ":feature:mission:data",
)
