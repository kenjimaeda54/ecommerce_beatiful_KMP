plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    //touchlab para view model
    alias(libs.plugins.touchlab.skie)

    //apolo
    alias(libs.plugins.apollo)

    //sqlite
    alias(libs.plugins.sqdelight)

    //serialize
    alias(libs.plugins.serialization)

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
            implementation(libs.datetime)
            implementation(libs.sql.coroutines.extensions)
            implementation(libs.kotlinx.serialization)
            implementation(libs.touchlab.stately.common)

        }

        iosMain.dependencies {
            implementation(libs.sql.native.driver)
            implementation(libs.touchlab.stately.isolate)
            implementation(libs.touchlab.stately.common)
        }

        androidMain.dependencies {
            implementation(libs.viewModel.ktx)
            implementation(libs.koin.android)
            implementation(libs.sql.android.driver)
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

//olha a diferença precisa ser graphql sem o s, nos arquivos qeu vou criar as query
//tambem quando o campo e obrigatorio precisa ser String!
//o arquivo gerado automatico e graphls
apollo {
    service("service") {
        packageName.set("com.ecommerce.beatiful")
        introspection {
            endpointUrl.set("https://graphql.canopyapi.co/")
            headers.set(mapOf("API-KEY" to "aedb8689-8a90-4f0f-ae16-7c983d35664d"))
            schemaFile.set(file("src/commonMain/graphql/schema.graphqls"))
            generateInputBuilders.set(true)
        }
    }
}



sqldelight {
    databases {
        create("EcommerceDB") {
            packageName.set("com.ecommerce.beatiful.db")

        }
    }
}