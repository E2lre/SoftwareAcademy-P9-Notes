FROM openjdk:8-jdk-alpine
EXPOSE 8085
VOLUME /log
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]