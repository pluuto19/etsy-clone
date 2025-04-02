# should be a maven image
FROM image:tag AS builder
WORKDIR /build
COPY . .
RUN mvn clean install -DskipTests

# should be a JRE only lightweight image
FROM image:tag AS layers
WORKDIR /layer
COPY --from=builder /build/target/app.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# should be a JRE only lightweight image for running the JAR
FROM image:tag
WORKDIR /app
RUN addgroup --system appusergroup && adduser -S -s /usr/sbin/nologin -G appusergroup appuser && chown -R appuser:appusergroup /app
COPY --from=layers /layer/dependencies/ ./
COPY --from=layers /layer/spring-boot-loader/ ./
COPY --from=layers /layer/snapshot-dependencies/ ./
COPY --from=layers /layer/application/ ./
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]