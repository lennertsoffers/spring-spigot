plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("signing")
}

group = "be.lennertsoffers"
version = "1.0.1"

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
    api("org.apache.velocity:velocity-engine-core:2.3")
    compileOnly("org.spigotmc:spigot:1.20.1-R0.1-SNAPSHOT")
}

java {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "be.lennertsoffers"
            artifactId = "spring-spigot"
            version = "1.0.1"

            from(components["java"])

            pom {
                signing {
                    val secretKey = findProperty("key").toString()
                    val password = findProperty("password").toString()
                    useInMemoryPgpKeys(secretKey, password)
                    sign(*publishing.publications.toTypedArray())
                }
                name.set("spring-spigot")
                description.set("Library providing Spring DI and annotation configuration for Spigot plugins ")
                url.set("https://github.com/lennertsoffers/spring-spigot")
                licenses {
                    license {
                        name.set("Apache 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                    }
                }
                developers {
                    developer {
                        id.set("lennertsoffers")
                        name.set("Lennert Soffers")
                        email.set("lennert.soffers@hotmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/lennertsoffers/spring-spigot.git")
                    developerConnection.set("scm:git:https://github.com/lennertsoffers/spring-spigot.git")
                    url.set("https://github.com/lennertsoffers/spring-spigot")
                }
            }
        }
    }

    repositories {
        maven {
            name = "OSSRH"
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.property("ossrh_username").toString()
                password = project.property("ossrh_password").toString()
            }
        }
    }
}
