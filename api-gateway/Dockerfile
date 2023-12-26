# creates an image of a current java app (openjdk:17 image is required)
# docker downloads the image from docker hub if one does not exist
FROM openjdk:17

# copies the jar file with arbitrary name located in path/on/host/machine
# in local system to path/on/container/myApp.jar file in container
COPY ./target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]