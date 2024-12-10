#Base/Parent Image
FROM openjdk:21-jdk-slim

#info of the maintainer
LABEL vishwdeepsingharia="vishwdeepsingharia"
#copy the jar file into our docker image
COPY target/accounts-0.0.1-SNAPSHOT.jar accounts-0.0.1-SNAPSHOT.jar
#Entry point
ENTRYPOINT ["java","-jar","accounts-0.0.1-SNAPSHOT.jar"]