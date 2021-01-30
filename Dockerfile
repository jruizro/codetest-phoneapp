FROM openjdk:11-jdk
LABEL maintainer=codetest-phoneapp artifact=codetest-phoneapp

EXPOSE 8000
ENV USER root

ADD build/libs/phoneapp-1.0.jar phoneapp.jar
ENTRYPOINT ["java","-jar","/phoneapp.jar"]
