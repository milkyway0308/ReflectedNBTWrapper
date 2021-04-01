plugins {
    kotlin("jvm") version "1.4.31"
    id("maven-publish")
}

buildscript {
    repositories {
        mavenCentral()
    }
}


group = "skywolf46"
version = properties["version"] as String

tasks {
    processResources {
        expand("version" to project.properties["version"])
    }
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.github.com/milkyway0308/CommandAnnotation") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }

    maven("https://maven.pkg.github.com/FUNetwork/SkywolfExtraUtility") {
        credentials {
            username = properties["gpr.user"] as String
            password = properties["gpr.key"] as String
        }
    }


}

dependencies {
    compileOnly(files("V:/API/Java/Minecraft/Bukkit/Spigot/Spigot 1.12.2.jar"))
    compileOnly("skywolf46:asyncdataloader:latest.release") {
        isChanging = true
        isTransitive = false
    }
    compileOnly("skywolf46:exutil:latest.release") {
        isChanging = true
        isTransitive = false
    }
    compileOnly("skywolf46:commandannotation:latest.release") {
        isChanging = true
        isTransitive = false
    }
}

publishing {
    repositories {
        maven {
            name = "Github"
            url = uri("https://maven.pkg.github.com/milkyway0308/ReflectedNBTWrapper")
            credentials {
                username = properties["gpr.user"] as String
                password = properties["gpr.key"] as String
            }
        }
    }
    publications {
        create<MavenPublication>("jar") {
            from(components["java"])
            groupId = "skywolf46"
            artifactId = "refnbt"
            version = properties["version"] as String
            pom {
                url.set("https://github.com/milkyway0308/ReflectedNBTWrapper.git")
            }
        }
    }
}
