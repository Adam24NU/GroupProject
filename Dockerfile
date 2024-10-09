FROM openjdk:latest
COPY ./target/WorldApp-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "WorldApp-1.0-SNAPSHOT-jar-with-dependencies.jar"]
