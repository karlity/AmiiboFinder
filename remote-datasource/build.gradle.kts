plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {

    namespace = "com.github.karlity.amiibofinder.remote.datasource"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "BASE_URL", "\"https://www.amiiboapi.com\"")
    }
    kotlin {
        // Use KSP Generated sources
        sourceSets.main {
            kotlin.srcDirs("build/generated/ksp/main/kotlin")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi.kotlin)

    // OkHttp
    implementation(libs.okHttp)
    implementation(libs.okHttp.mockwebserver)
    implementation(libs.okHttp.logging)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(platform(libs.koin.annotations.bom))
    implementation(libs.koin.annotations)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    ksp(libs.koin.ksp.compiler)

    // Testing
    testImplementation(libs.test.junit)
    testImplementation(libs.test.koin)
    testImplementation(libs.test.koin.android)
    testImplementation(libs.test.koin.junit)
    testImplementation(libs.test.mockK)
    testImplementation(libs.test.mockK.android)
    testImplementation(libs.test.mockK.agent)
    testImplementation(libs.test.coroutines)
}
