FROM openjdk:17-jdk-alpine
#RUN apk update
COPY backendJavaJpaToken/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]