plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    //touchlab para view model
    alias(libs.plugins.touchlab.skie)

    //apolo
    alias(libs.plugins.apollo)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.ktx)
            implementation(libs.koin.core)
            implementation(libs.apollo.runtime)
        }

        iosMain.dependencies {

        }

        androidMain.dependencies {
            implementation(libs.viewModel.ktx)
            implementation(libs.koin.android)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.ecommerce.beatiful"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

apollo {
    service("service") {
        packageName.set("com.ecommerce.beatiful")
        introspection {
            endpointUrl.set("https://graphql.canopyapi.co/")
            headers.set(mapOf("API-KEY" to "6385f7dd-ffa3-488f-b024-1b2af735bec5"))
            schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
            generateInputBuilders.set(true)
        }
    }
}
