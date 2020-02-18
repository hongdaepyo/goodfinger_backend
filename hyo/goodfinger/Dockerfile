FROM openjdk:13-alpine
LABEL maintainer="hyovel00@gmail.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/goodfinger-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} goodfinger-hyo.jar
ENTRYPOINT ["java","-jar","/goodfinger-hyo.jar"]
