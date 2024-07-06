plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleDevtoolsKsp)
    id("kotlin-parcelize")
}

android {
    namespace = "com.mobbelldev.githubuser"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mobbelldev.githubuser"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        buildConfigField("String", "API_KEY", "\"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\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    // square/retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // square/okhttp
    implementation(libs.logging.interceptor)

    // bumptech/glide
    implementation(libs.glide)

    // androidx-ktx
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.datastore.preferences)

    // androidx-library
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // androidx-room
    implementation(libs.androidx.room.runtime)

    // ksp-room
    ksp(libs.room.compiler)

    // viewpager
    implementation(libs.androidx.viewpager2)

    // material-design
    implementation(libs.material)

    // android-junit
    testImplementation(libs.junit)

    // androidx-test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}