import org.jetbrains.kotlin.com.intellij.openapi.util.SystemInfo
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"

    id("org.springframework.boot") version "3.3.4"
    id("com.google.cloud.tools.jib") version "3.4.2"
}

repositories {
    mavenCentral()
}

jib {
    from {
        image = "docker://openjdk:21-jdk-slim"
        container.environment = mapOf("TZ" to "Asia/Seoul")
        container.creationTime = "USE_CURRENT_TIMESTAMP"

        if (SystemInfo.isMac && "aarch64" in SystemInfo.OS_ARCH) {
            platforms {
                platform {
                    architecture = "arm64"
                    os = "linux"
                }
            }
        }
    }

    to.image = "sample-app-for-trivy"

}

dependencies {
    implementation(platform(SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
}
