FROM openjdk:8-jdk-alpine

ADD target/smddevTestTask-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongodb/products","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
