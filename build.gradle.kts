plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "br.com.cevanotes"
version = "1.0-SNAPSHOT"

// Configuração para deployment
application {
    mainClass.set("br.com.cevanotes.Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:6.6.0")
    implementation("org.jdbi:jdbi3-core:3.43.0")
    implementation("org.jdbi:jdbi3-sqlobject:3.43.0")
    implementation("com.h2database:h2:2.2.224")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.0")
    implementation("org.slf4j:slf4j-simple:2.0.9")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.javalin:javalin-testtools:6.6.0")
    testImplementation("com.squareup.okhttp3:okhttp:4.12.0")
    testImplementation("org.mockito:mockito-core:5.12.0") // ou versão mais recente
    testImplementation("org.mockito:mockito-junit-jupiter:5.12.0") // integração com JUnit 5
}

tasks.test {
    useJUnitPlatform()
}

// Task para criar JAR executável com todas as dependências (Fat JAR)
tasks.jar {
    enabled = false
    dependsOn(tasks.shadowJar)
}

tasks.shadowJar {
    archiveClassifier.set("")
    manifest {
        attributes(mapOf("Main-Class" to "br.com.cevanotes.Main"))
    }
}