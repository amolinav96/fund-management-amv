# Usar una imagen base de Maven que esté disponible y luego instalar JDK 21
FROM jelastic/maven:3.9.5-openjdk-21 AS build
#FROM openjdk:21-jdk AS build


# Establecer el directorio de trabajo en la imagen Docker
WORKDIR /app

# Copiar los archivos de configuración de Maven y el archivo de proyecto POM
COPY pom.xml .
COPY .mvn .mvn

# Descargar las dependencias sin construir el proyecto
RUN mvn dependency:go-offline

# Copiar todo el código fuente del proyecto
COPY src ./src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Usar una imagen base de OpenJDK 21 para ejecutar la aplicación
FROM openjdk:21

# Establecer el directorio de trabajo en la imagen Docker
WORKDIR /app

# Copiar el archivo JAR generado desde la fase de construcción
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que la aplicación se ejecutará
EXPOSE 8060

# Definir el comando de inicio de la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]