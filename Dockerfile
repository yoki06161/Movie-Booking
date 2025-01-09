FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY . /app
RUN ./gradlew clean build -x test

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/build/libs/movie-booking-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]