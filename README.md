# Dobby OAuth2 Spring Example

An example for Game integration with Dobby API for Spring Boot(Java).

## Setup Instructions
1. [Install JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 

2. [Install maven](https://maven.apache.org/install.html)

3. Build project:

  `mvn package`
  
4. Start server:

  `java -jar target/dobby-game-demo.jar`

  This starts the server on port `8080` listening to all interfaces.
  
  [Open in browser](http://localhost:8080)


## Configuration
In case you need to change any properties (credential, environment, etc):
modify `./src/main/resources/application.properties` and rebuild the project



