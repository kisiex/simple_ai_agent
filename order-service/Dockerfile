FROM eclipse-temurin:21-jre

LABEL authors="Geeziel"

WORKDIR /app

COPY target/order-service-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]