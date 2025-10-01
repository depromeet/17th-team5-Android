pluginManagement {
    includeBuild("build-logic")

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

rootProject.name = "Depromeet"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")
include(":core")
include(":core:domain")
include(":core:data")
include(":core:localdatasource")
include(":core:remotedatasource")
include(":core:retrofit")
include(":core:designsystem")
include(":core:model")
include("features")
include(":features:retrospect")
include(":features:search")
include(":features:principle")
include(":features:home")
include(":features:feedback")

include(":features:reasons")
include(":core:logger")
