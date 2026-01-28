plugins {
    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.ksp)
}

android {
    namespace = "com.owenoneil.aisecuritycamera"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.owenoneil.aisecuritycamera"
        minSdk = 36
        targetSdk = 36

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

    // Material
    implementation(libs.material)

    // Supabase
    implementation("io.github.jan-tennert.supabase:supabase-kt:3.2.1")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.2.1")
    implementation("io.github.jan-tennert.supabase:auth-kt:3.2.1")
    implementation("io.github.jan-tennert.supabase:realtime-kt:3.2.1")

    // Room
//    implementation(libs.room.runtime)
//    implementation(libs.room.ktx)
//    implementation(libs.room.compiler)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}