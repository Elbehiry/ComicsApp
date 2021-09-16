plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}


android {
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-proguard-rules.pro")
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.incremental"] = "true"
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        val options = this
        options.jvmTarget = "1.8"
    }

    buildTypes {
        getByName("release") {}
        getByName("debug") {}
    }

    sourceSets {
        getByName("androidTest").assets.srcDirs("$projectDir/schemas")
    }

    packagingOptions {
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }

}

dependencies {
    api(platform(project(":depconstraints")))
    kapt(platform(project(":depconstraints")))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Architecture Components
    implementation(Libs.LIFECYCLE_LIVE_DATA_KTX)
    implementation(Libs.LIFECYCLE_VIEW_MODEL_KTX)
    implementation(Libs.ROOM_KTX)
    implementation(Libs.ROOM_RUNTIME)
    kapt(Libs.ROOM_COMPILER)
    testImplementation(Libs.ARCH_TESTING)

    // Utils
    api(Libs.TIMBER)
    implementation(Libs.CORE_KTX)

    // OkHttp
    implementation(Libs.OKHTTP)
    implementation(Libs.OKHTTP_LOGGING_INTERCEPTOR)
    testImplementation(Libs.OKHTTP_MOCK_SERVER)

    // Retrofit
    api(Libs.RETROFIT)
    api(Libs.MOSHI)
    api(Libs.MOSHI_KOTLIN)
    api(Libs.MOSHI_RETROFIT)

    // Kotlin
    implementation(Libs.KOTLIN_STDLIB)

    // Coroutines
    api(Libs.COROUTINES)
    testImplementation(Libs.COROUTINES_TEST)
    implementation(Libs.COROUTINES_PLAY_SERVICE)

    // Dagger Hilt
    implementation(Libs.HILT_ANDROID)
    kapt(Libs.HILT_COMPILER)

    // Unit tests
    testImplementation(Libs.JUNIT)
    testImplementation(Libs.HAMCREST)
    testImplementation(Libs.MOCKITO_CORE)
    testImplementation(Libs.MOCKITO_KOTLIN)
    testImplementation(Libs.FAKER)
    testImplementation(Libs.TURBINE)
    testImplementation(Libs.EXT_JUNIT)
    testImplementation(Libs.ASSERT_J)
    testImplementation(Libs.MOCKK)

    androidTestImplementation(Libs.ARCH_TESTING)
    androidTestImplementation(Libs.RUNNER)
    androidTestImplementation(Libs.EXT_JUNIT)
    androidTestImplementation(Libs.ASSERT_J)
    androidTestImplementation(Libs.TURBINE)
    androidTestImplementation(Libs.ROOM_TESTING)
    androidTestImplementation(Libs.COROUTINES_TEST)
    androidTestImplementation(Libs.FAKER)
}