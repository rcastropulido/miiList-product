# BUILD ######################################################
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN mvn -pl miiList-product -am clean package -DskipTests
################################################################

# RUN DEV ######################################################
FROM eclipse-temurin:21-jre-alpine AS dev

WORKDIR /app
COPY --from=build /app/miiList-product/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xmx256m -Xms128m -Dspring.profiles.active=dev"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
################################################################

# RUN PRO ######################################################
FROM eclipse-temurin:21-jre-alpine AS pro

WORKDIR /app
COPY --from=build /app/miiList-product/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-Xmx512m -Xms256m -Dspring.profiles.active=pro"

# No root user
RUN addgroup miigroup && adduser -D -G miigroup miiuser
USER miiuser

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
################################################################

# RUN PRO DISTROLESS (CLOUD) ###################################
FROM gcr.io/distroless/java21-debian12:nonroot AS pro-cloud
WORKDIR /app

COPY --from=build /app/miiList-product/target/*.jar app.jar

EXPOSE 8080

ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=50.0 -XX:+UseContainerSupport -XX:+OptimizeStringConcat -XX:+UseStringDeduplication"

ENTRYPOINT ["java", "-Dspring.profiles.active=pro", "-jar", "app.jar"]
################################################################