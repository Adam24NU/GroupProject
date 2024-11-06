FROM openjdk:latest
WORKDIR /tmp
COPY ./target/WorldApp-0.1-alpha-2-jar-with-dependencies.jar /tmp
ENTRYPOINT ["java", "-jar", "WorldApp-0.1-alpha-2-jar-with-dependencies.jar"]
