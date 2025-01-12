import org.jetbrains.kotlin.gradle.plugin.sources.dependsOnClosure
import java.util.Properties

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

    //build config
    alias(libs.plugins.config.build)

    //kotest
    alias(libs.plugins.kotest.multiplaform)

    //report test
    jacoco

}


tasks.register<JacocoReport>("jacocoCoverageVerification") {
    dependsOn(tasks.withType<Test>())


    reports {
        html.required.set(true)
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/shared"))
    }

    classDirectories.setFrom(
        fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/debug") {
            exclude("**/R.class")
            exclude("**/R\$*.class")
            exclude("**/BuildConfig.*")
            exclude("**/*\$Creator.*")
        }
    )

    sourceDirectories.setFrom(
        files(
            "src/commonMain/kotlin",
            "src/androidMain/kotlin"
        )
    )

    executionData.setFrom(
        files("${layout.buildDirectory.get()}/jacoco/testDebugUnitTest.exec")
    )
}




kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    val apiKey = properties.getProperty("API_KEY")
    val supabaseKey = properties.getProperty("SUPABASE_KEY")
    buildConfig {
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
        buildConfigField("String", "SUPABASE_KEY", "\"$supabaseKey\"")
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
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.supabase.auth)
        }

        iosMain.dependencies {
            implementation(libs.sql.native.driver)
            implementation(libs.touchlab.stately.isolate)
            implementation(libs.touchlab.stately.common)
            implementation(libs.ktor.client.darwin)
        }

        androidMain.dependencies {
            implementation(libs.viewModel.ktx)
            implementation(libs.koin.android)
            implementation(libs.sql.android.driver)
            implementation(libs.ktor.client.android)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test-annotations-common"))
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.koin.test)

            tasks.withType<Test> {
                finalizedBy(tasks.named("jacocoCoverageVerification"))
            }

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
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    val apiKey = properties.getProperty("API_KEY")
    service("service") {
        packageName.set("com.ecommerce.beatiful")
        introspection {
            endpointUrl.set("https://graphql.canopyapi.co/")
            headers.set(mapOf("API-KEY" to apiKey))
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