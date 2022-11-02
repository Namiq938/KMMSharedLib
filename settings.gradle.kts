pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ExpenceCalculatorLib"
include(":shared")
includeBuild("plugins/multiplatform-swiftpackage-m1_support")
