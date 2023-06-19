plugins {
    id("java")
    id("java-library")
}

group = "be.lennertsoffers"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    api("org.springframework:spring-context:6.0.10")
    compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT")
    implementation("org.apache.velocity:velocity-engine-core:2.3")
}
