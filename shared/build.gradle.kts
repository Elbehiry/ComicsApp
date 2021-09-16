/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        val urlName = "xkcd_BASE_URL"
        val baseUrl = "\"http://xkcd.com/\""

        getByName("release") {
            buildConfigField("String", urlName, baseUrl)
        }
        getByName("debug") {
            buildConfigField("String", urlName, baseUrl)
        }
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
    api(project(":model"))

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
