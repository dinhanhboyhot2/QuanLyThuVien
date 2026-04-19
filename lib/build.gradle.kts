plugins {
    id("java")
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.microsoft.sqlserver:mssql-jdbc") // Driver SQL Server (NFR21)
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}