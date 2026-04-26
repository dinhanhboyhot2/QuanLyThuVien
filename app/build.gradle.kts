plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.QuanLyThuVien"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.QuanLyThuVien"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Xóa toàn bộ nội dung cũ trong dependencies và thay bằng đoạn này
    dependencies {
        // 1. Dùng biến từ file libs.versions.toml
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        implementation(libs.androidx.core.ktx)

        // 2. Ép phiên bản ổn định (Hạ cấp để khớp SDK 35)
        implementation("androidx.activity:activity:1.9.3")
        implementation("androidx.core:core-ktx:1.15.0")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
        implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

        // 3. Test
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)

        // Unit Test
        testImplementation ("junit:junit:4.13.2")
        testImplementation ("org.mockito:mockito-core:5.11.0")

        // Android Instrumentation Test (khuyên thêm)
        androidTestImplementation ("androidx.test.ext:junit:1.2.1")
        androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    }

