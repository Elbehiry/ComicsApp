plugins {
    id("java-library")
    kotlin("jvm")
}

dependencies {
    api(platform(project(":depconstraints")))
    implementation(project(":model"))

    // Kotlin
    implementation(Libs.KOTLIN_STDLIB)

    // Test
    implementation(Libs.JUNIT)
    implementation(Libs.FAKER)
    api(Libs.COROUTINES_TEST)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
