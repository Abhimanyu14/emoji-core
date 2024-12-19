plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    application
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.17.0"
    id("maven-publish")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
    explicitApi()
}

application {
    mainClass.set("MainKt")
}

/**
 * Source for publishing source code
 * https://stackoverflow.com/a/70677010/9636037
 */
tasks.register(
    name = "androidReleaseSourcesJar",
    type = Jar::class,
) {
    archiveClassifier.set("sources")

    from(kotlin.sourceSets["main"].kotlin.srcDirs)
}

project.afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.Abhimanyu14"
                artifactId = "emoji-core"
                version = "1.0.13"

                // Publish both the main JAR and the sources JAR
                from(components["kotlin"])
                artifact(tasks.getByName("androidReleaseSourcesJar"))
            }
        }
    }
}
