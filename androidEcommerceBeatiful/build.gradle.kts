plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)

    //jacoco
    jacoco
}

//abaixo como fazer teste apenas para androidTest que e o instrument test
tasks.register<JacocoReport>("jacocoCoverageVerification") {
    dependsOn("createDebugCoverageReport")

    reports {
        html.required.set(true)
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/android"))
    }

    classDirectories.setFrom(
        fileTree("${layout.buildDirectory.get()}/tmp/kotlin-classes/debug") {
            //preciso incluir quais classes serao feito o cover
            include("**/com/ecommerce/beatiful/android/ui/screens/**")
            exclude("**/R.class")
            exclude("**/R\$*.class")
            exclude("**/BuildConfig.*")
            exclude("**/*\$Creator.*")
        }
    )

    sourceDirectories.setFrom(
        files(
                "src/main/java",
        )
    )

    executionData.setFrom(
        fileTree(layout.buildDirectory) {
            include("outputs/code_coverage/debugAndroidTest/connected/**/*.ec")
        }

    )
}


android {
    namespace = "com.ecommerce.beatiful.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.ecommerce.beatiful.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        //test
        //java/InstrumentationTestRunner.kt
        testInstrumentationRunner  = "com.ecommerce.beatiful.android.InstrumentationTestRunner"

    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }

        //paara ativar coverage de teste de instruments
        //soa testes de ui
        debug {
            enableAndroidTestCoverage = true
        }

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)

    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.bottom.navigation)

    //koin
    implementation(libs.koin.androidx.core)
    implementation(libs.koin.android)

    //lottie
    implementation(libs.lottie.compose)

    //coil
    implementation(libs.coil.image)

    //test instrumentation
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.test.ext)
    androidTestUtil(libs.test.orchestrator)
    implementation(libs.androidx.ui.test.junit4.android)
    debugImplementation(libs.test.manifest)

    tasks.withType<Test> {
        finalizedBy(tasks.named("jacocoCoverageVerification"))
    }

}