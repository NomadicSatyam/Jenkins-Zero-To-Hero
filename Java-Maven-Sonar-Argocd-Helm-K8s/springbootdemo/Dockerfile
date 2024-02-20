# You can change this base image to anything else
# But make sure to use the correct version of Java
FROM openjdk:17

# Simply the artifact path
ARG artifact=target/spring-boot-demo.jar

WORKDIR /opt/app

COPY ${artifact} app.jar

# This should not be changed
ENTRYPOINT ["java","-jar","app.jar"]






