FROM eclipse-temurin:17-jre-jammy



# Copy the built SNAPSHOT JAR from your local target/build folder
# (Adjust "target/*.jar" if you are using Gradle "build/libs/*.jar")
COPY target/*-SNAPSHOT.jar app.jar



# Execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]