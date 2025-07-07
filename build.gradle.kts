plugins {
	java
	id("org.springframework.boot") version "3.5.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco")
}

group = "com.thiago"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("org.mapstruct:mapstruct:1.6.3")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

	compileOnly("org.projectlombok:lombok:1.18.32")
	annotationProcessor("org.projectlombok:lombok:1.18.32")
	testImplementation ("org.projectlombok:lombok:1.18.32")

	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

	implementation ("org.springframework.boot:spring-boot-starter-validation")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")

	testImplementation ("org.junit.jupiter:junit-jupiter:5.7.1")
	testRuntimeOnly ("org.junit.platform:junit-platform-launcher")

	implementation("org.flywaydb:flyway-core:10.16.0")
	implementation("org.flywaydb:flyway-database-oracle:10.16.0")
	implementation("com.oracle.database.jdbc:ojdbc11:21.9.0.0")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.named<Test>("test") {
	useJUnitPlatform()

	maxHeapSize = "1G"

	testLogging {
		events("passed")
	}
}

tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.jacocoTestReport)

	violationRules {
		rule {
			element = "CLASS"
			includes = listOf("com.thiago.desafiovotacao.service.*")

			limit {
				counter = "INSTRUCTION"
				value = "COVEREDRATIO"
				minimum = "0.80".toBigDecimal()
			}
		}
	}

	classDirectories.setFrom(
		files(classDirectories.files.map {
			fileTree(it) {
				include("com/thiago/desafiovotacao/service/**/*.class")
			}
		})
	)
}


