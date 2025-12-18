# Spring Elevator Emulator

A fairly basic attempt at creating an elevator emulator system, where I apply
my knowledge of Spring Boot and React, while learning with 
STOMP WebSockets. I wanted to fully set up an emulated system just via 
Spring Boot, in a way that makes sense to me!

Features Spring Boot backend & React Frontend.

Current Version: `1.0.0`

---
## Download and Setup
There are 2 methods of downloading and setup. The easiest is via DockerHub for
immediate usage.

### Docker Setup
1. Download the Docker Image

```shell
docker pull larrythexu88/elevatoremu:<version-tag>
```

2. Run the Docker Image

```shell
docker run -p 8080:8080 larrythexu88/elevatoremu:<version-tag>
```

### Maven Setup
1. Clone this GitHub repo

```shell
git clone https://github.com/larrythexu/SpringElevator.git
```

2. Enter into the directory and build the JAR with Maven

```shell
cd ElevatorEmu
./mvnw clean package -DskipTests
```

3. This will create a JAR file, which we can run using Java
```shell
java -jar target/<ElevatorEmu-Version>.jar
```

---

## Using the Emulator
After running the Docker Image or Running the JAR file, 
the emulator will operate on `localhost:8080`. Enter 
that in your browser, giving you access to 
the React webpage. You do not have to interact with the 
emulator through this, but it's easier to. You could also 
directly call the endpoints via Postman. (See all API endpoints 
in the `*Controller.java` files in this project)

Initiate the emulator by setting the initial number of elevators.

Then press the `Start` button to start the emulator process. You 
can start adding destination floors now and the emulator should be 
assigning each floor to an elevator.

You can also adjust the Selection Strategy, which decides how an
elevator is selected:
 - Simple Selector: Basic round-robin style
 - Proximity Selector: Selects based on closest distance and same direction
