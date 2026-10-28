# ---------- Stage 1: build the JAR with the Gradle wrapper ----------
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle
RUN ./gradlew dependencies --no-daemon -q > /dev/null
COPY src src
RUN ./gradlew bootJar -x test --no-daemon

# ---------- Stage 2: small runtime image ----------
FROM eclipse-temurin:17-jre
LABEL maintainer="Dauletkan Nursultan <dauletkannursultan@gmail.com>" \
      org.opencontainers.image.title="dauletkan-nursultan-student-api" \
      student.group="IT2-2313" \
      student.id="37037"
WORKDIR /app
RUN useradd --system --uid 1001 appuser
COPY --from=build /app/build/libs/dauletkan-nursultan-student-api.jar app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
