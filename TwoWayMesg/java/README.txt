# These instructions are for running the TwoWayMesg application written in Java

## Compile the client and server programs
```sh
javac TwoWayMesgServer.java
javac TwoWayMesgClient.java
```

## Running the Server
First, run the server program with a port number (say 50000) and a server name:
```sh
java TwoWayMesgServer 50000 <server name>
```

## Running the Client
Then, run the client program with the server's whereabouts and a client name:
```sh
java TwoWayMesgClient <server name> 50000 <client name>
```

## Interaction
- Type messages at the client and see them displayed at the server.
- Type messages at the server and see them displayed at the client.

## Ending the Client
To end the client, type Ctrl-D (on Windows Ctrl-Z followed by return). This is referred to as EOF (end of file), meaning end of input. Then, the server will also quit saying the client closed the connection.
