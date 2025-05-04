FROM maven:3.9.9-eclipse-temurin-21-alpine@sha256:6b210588b5a7a3f39e88c96c302a0ea74ce77c15022d3cc1d2cd810dceb160b4 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src src
RUN mvn package -DskipTests

FROM eclipse-temurin:21.0.6_7-jre-alpine-3.21@sha256:f184bb601f9e6068dd0a92738764d1ff447ab68c15ddbf8c303c5c29de9a1df8
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
RUN addgroup --system appgroup && \
    adduser --system --ingroup appgroup appuser && \
    chown -R appuser:appgroup /app
USER appuser
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]