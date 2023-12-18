FROM openjdk:17

WORKDIR /app

COPY build/libs/CliTaxi-1.0-SNAPSHOT.jar ./

CMD ["java","-jar","CliTaxi-1.0-SNAPSHOT.jar"]