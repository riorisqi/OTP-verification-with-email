plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.sendmailtest"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("com.sun.mail:javax.mail:1.6.2")
    implementation("com.github.bastiaanjansen:otp-java:2.0.3")
    implementation("com.google.guava:guava:33.1.0-jre")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}