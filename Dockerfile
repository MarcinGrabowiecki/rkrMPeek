FROM openjdk:10-jre-slim

WORKDIR /app
COPY ./build/libs/recruitment-0.0.1-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "recruitment-0.0.1-SNAPSHOT.jar"]