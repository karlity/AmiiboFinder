plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.github.karlity.amiibofinder"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.karlity.amiibofinder"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":remote-datasource"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.compose.navigation)
    implementation(libs.compose.animation)
    implementation(libs.compose.coil)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(platform(libs.koin.annotations.bom))
    implementation(libs.koin.annotations)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.androidx.compose)
    ksp(libs.koin.ksp.compiler)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Testing
    // Testing
    testImplementation(libs.test.junit)
    testImplementation(libs.test.junit.androidx)
    testImplementation(libs.test.koin)
    testImplementation(libs.test.koin.android)
    testImplementation(libs.test.koin.junit)
    testImplementation(libs.test.mockK)
    testImplementation(libs.test.mockK.android)
    testImplementation(libs.test.mockK.agent)
    testImplementation(libs.test.coroutines)
    implementation("androidx.arch.core:core-testing:2.1.0")
    androidTestImplementation(libs.test.junit.androidx)
    androidTestImplementation(libs.test.espresso.core)
}
