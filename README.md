# DevTest 
#### Hishab coding challenge
A family-friendly game. It is a simple board game, where we play in turns using dice. This dice is provided by Hishab because it is a special dice, you could say it is their family’s lucky charm!
We can find the dice here:
http://developer-test.hishab.io/api/v1/roll-dice

### The rules of the game are:
* There is a maximum of 4 players.
* Each player has a name and age.
* The first player to get a total sum of 25 is the winner. A player does not have to get 25 exactly (>=25 is OK). The number 25 should be configurable.
* To get started the player will need to get 6. If the player gets 1-5 they will then have to wait for their turn before having another go.
* When finally hitting the number 6 the player will have to throw again to determine the starting point. Getting a 6 on the first try will give you 0.
* Each time a player hits number 4, he will get -4 from the total score.
* If a player hits a 4 after hitting the first 6, they do not get a negative score but will have to roll another 6 before they start accumulating points.
* Each time a player hits the number 6 he will then get one extra throw.
* You could show output through the console/terminal or if you want to show some frontend skills that is a bonus. Both options are fine.

#### Deadline: 8th February 2023

### Technology
* Language: Java 17
* Build Tool: Gradle
* Spring Boot: 2.7.8
* Database: H2
* REST API Doc: Swagger (http://localhost:8080/swagger-ui/index.html#)
* Containerization: Docker
* Version Control: Git
* Unit Test: JUnit, Mockito

### How to Build & Run (Using Gradle)
To build the project execute the following command:
```
./gradlew clean build
```
Executing that command will generate a jar under "build/libs" folder which you can run using following command: 
```
java -jar devtest-0.0.1-SNAPSHOT.jar
```
or, You can run the app directly by following command:
```
./gradlew bootRun
```

### How to Build & Run (Using Docker)
First, build the project using gradle using following command:
```
./gradlew clean build
```
Then, We can build an image with the following command:
```
docker build -t hishab/devtest .
```
We can run it by running the following command:
```
docker run -p 8080:8080 hishab/devtest
```
use -d flag to run in the background:
```
docker run -d -p 8080:8080 hishab/devtest
```
### Rest APIs
After running the application, it will be up & running at: 
http://localhost:8080

You can access the Swagger Doc by going to: 
http://localhost:8080/swagger-ui/index.html#

### Game Config
Before running the app, you can also config the game's maximum & minimum player to play as well as the winning score 
through this file: `application.yml`

### Contact
Jamilur Rahman
Software Engineer
Brain Station 23 Ltd.
Email: [jamilur.rahman@brainstation-23.com](mailto:jamilur.rahman@brainstation-23.com)

