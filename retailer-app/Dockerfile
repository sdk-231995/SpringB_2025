FROM arm64v8/openjdk:11

WORKDIR /retailer

COPY target/retailer-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]