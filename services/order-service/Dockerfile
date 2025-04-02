# should be a maven image
FROM maven:3.9.9-eclipse-temurin-21-alpine@sha256:6b210588b5a7a3f39e88c96c302a0ea74ce77c15022d3cc1d2cd810dceb160b4 AS builder
WORKDIR /build
COPY . .
RUN mvn clean install -DskipTests

# should be a JRE only lightweight image
FROM eclipse-temurin:21.0.6_7-jre-alpine-3.21@sha256:f184bb601f9e6068dd0a92738764d1ff447ab68c15ddbf8c303c5c29de9a1df8 AS layers
WORKDIR /layer
COPY --from=builder /build/target/app.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# should be a JRE only lightweight image for running the app
FROM eclipse-temurin:21.0.6_7-jre-alpine-3.21@sha256:f184bb601f9e6068dd0a92738764d1ff447ab68c15ddbf8c303c5c29de9a1df8
WORKDIR /app
RUN addgroup --system appusergroup && adduser -S -s /usr/sbin/nologin -G appusergroup appuser && chown -R appuser:appusergroup /app
COPY --from=layers /layer/dependencies/ ./
COPY --from=layers /layer/spring-boot-loader/ ./
COPY --from=layers /layer/snapshot-dependencies/ ./
COPY --from=layers /layer/application/ ./
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]