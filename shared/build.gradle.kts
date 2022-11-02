import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("com.chromaticnoise.multiplatform-swiftpackage-m1-support")
    id("maven-publish")
}

version = "1.2"
group = "com.namig.expencecalculator.library"

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    val xcf = XCFramework("Shared")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "Shared"
            xcf.add(this)
        }
    }

    //1
    multiplatformSwiftPackage {
        //2
        xcframeworkName("Shared")
        //3
        swiftToolsVersion("5.3")
        //4
        targetPlatforms {
            iOS { v("13") }
        }
        //5
        outputDirectory(File(projectDir, "sharedlib"))
    }

    publishing {
        repositories {
            maven {
                //1
                url = uri("https://maven.pkg.github.com/Namiq938/KMMSharedLib")
                //2
                credentials {
                    username = "Namiq938"
                    password = "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"
                }
                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "shared"
        }
    }
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.namig.expencecalculator.library"
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}