FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ARG key
ENV decryptKey=$key
EXPOSE 8080
EXPOSE 27017
ENTRYPOINT ["java","-Djasypt.encryptor.password=${decryptKey}","-jar","/app.jar","production"]