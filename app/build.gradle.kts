val transitive = false

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.diplom.travelguide"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.diplom.travelguide"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}


dependencies {

    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2") // - для подключения библиотеки интеграции или настраивать Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.github.bumptech.glide:recyclerview-integration:4.14.2") {
        // Excludes the support library because it's already included by Glide.
        transitive  // первая строка сверху
    } // для синхронизации с recyclerView

    // яндекс карты
    implementation ("com.yandex.android:maps.mobile:4.6.1-lite")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}