plugins {
    java
    id("com.gradleup.shadow") version "9.3.2"
}

group = "io.github.thebusybiscuit"
version = "1.0.0"
description = "ExtraGear is a Slimefun addon that adds extra armor sets and tools."

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly(fileTree("${rootDir}/../Slimefun5/build/libs") { include("*.jar") })
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

configurations {
    testImplementation {
        extendsFrom(configurations.compileOnly.get())
    }
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }

    jar {
        enabled = false
    }

    shadowJar {
        archiveFileName.set("ExtraGear v${project.version}.jar")
        relocate("org.bstats", "io.github.thebusybiscuit.extragear.bstats")
        exclude("META-INF/**")
    }

    build {
        dependsOn(shadowJar)
    }

    test {
        useJUnitPlatform()
    }
}
