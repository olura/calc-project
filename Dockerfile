FROM maven:3.9.4-eclipse-temurin-17-alpine as builder
LABEL authors="foodmen"
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package

FROM bellsoft/liberica-runtime-container:jre-17-musl
COPY --from=builder /app/target/calc-projects-latest.jar /app/calc-projects.jar
ENTRYPOINT ["java","-jar","/app/calc-projects.jar"]