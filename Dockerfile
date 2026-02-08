FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY target/student-management-system-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]