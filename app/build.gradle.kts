plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.kaiitakura.glyphtoy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kaiitakura.glyphtoy"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Nothing の Glyph Matrix SDK。ライセンス上再配布できないため、
    // 各自ダウンロードして app/libs/ に置く（README 参照）。
    implementation(files("libs/glyph-matrix-sdk-2.0.aar"))
}
