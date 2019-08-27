FROM maven:3.6.1-jdk-8-slim AS MAVEN_TOOL_CHAIN

COPY pom.xml /tmp/
COPY src/ /tmp/src/
WORKDIR /tmp/
RUN mvn -Dmaven.test.skip=true package

FROM openjdk:8-jdk-alpine

COPY --from=MAVEN_TOOL_CHAIN /tmp/target/smddevTestTask-*.jar app.jar

ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongodb/products","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
