plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    application
}

group = "org.sodgutt.homesource"
version = "1.0.0"
application {
    mainClass.set("org.sodgutt.homesource.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.bundles.ktor.backend)
    implementation(libs.bundles.database)
    implementation(libs.bundles.common)

    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.koin.test)
    testImplementation(libs.ktor.serialization)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockk)
}