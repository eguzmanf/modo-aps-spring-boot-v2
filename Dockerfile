FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=target/demo-1.0.1-SNAPSHOT.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]