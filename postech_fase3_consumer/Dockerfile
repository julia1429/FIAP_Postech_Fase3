# ===== Stage 1: Build =====
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copia arquivos do projeto
COPY pom.xml .
COPY src ./src

# Build do JAR
RUN mvn clean package -DskipTests

# ===== Stage 2: Runtime =====
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copia o JAR gerado
COPY --from=build /app/target/*.jar app.jar

# Porta específica do módulo
EXPOSE 8083  

ENTRYPOINT ["java", "-jar", "app.jar"]
