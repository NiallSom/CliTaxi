FROM openjdk:17

WORKDIR /app

COPY build/libs/CliTaxi-1.0-SNAPSHOT.jar /app/app.jar

CMD ["java","-jar","app.jar"]