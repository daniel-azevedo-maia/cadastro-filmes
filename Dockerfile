FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/my-cine-checker-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh

EXPOSE 8080

CMD ["java", "-jar", "/app.jar"]
