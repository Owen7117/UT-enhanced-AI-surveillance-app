plugins {
    alias(libs.plugins.android.application)
    kotlin("plugin.serialization") version "1.9.23"
}

android {
    namespace = "com.owenoneil.aisecuritycamera"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.owenoneil.aisecuritycamera"
        minSdk = 26
        targetSdk = 36
        compileSdk = 36

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {

    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)

    implementation("io.ktor:ktor-client-core:3.4.0")
    implementation("io.ktor:ktor-client-android:3.4.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.4.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.0")

    implementation("io.github.jan-tennert.supabase:supabase-kt:3.3.0")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.3.0")
    implementation("io.github.jan-tennert.supabase:auth-kt:3.3.0")
    implementation(libs.places)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}