FROM openjdk:11
ARG JAR_FILE=target/InternetShop-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} internetshop.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/internetshop.jar"]