# STAGE 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el POM y descargamos dependencias primero
COPY pom.xml .
RUN mvn -q -e -B dependency:go-offline

# Copiamos el resto del código
COPY src ./src

# Compilamos la aplicación omitiendo tests
RUN mvn -q -e -B clean package -DskipTests


# STAGE 2: Runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el JAR construido
COPY --from=build /app/target/*.jar app.jar

# Variables opcionales de JVM
ENV JAVA_OPTS=""

# Exponemos el puerto
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
