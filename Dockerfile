FROM openjdk:17
ADD target/*.jar player.jar
ENTRYPOINT ["java", "-jar", "player.jar"]