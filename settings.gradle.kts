enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
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
        //maneira de adicionar url

//        maven {
//            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
//        }

    }
}

rootProject.name = "Ecommerce_Beatiful"
include(":androidEcommerceBeatiful")
include(":shared")