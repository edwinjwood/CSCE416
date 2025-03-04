# Group Chat Application

This is a simple group chat application written in Java. It allows multiple clients to connect to a server and broadcast messages to all connected clients.

## Compilation

To compile the server and client programs, use the following commands:

```sh
javac GroupChatServer.java
javac GroupChatClient.java
```

## Running the Server

To start the server, run the following command with a specified port number (e.g., 50000):

```sh
java GroupChatServer 50000
```

## Running the Client

To start a client, run the following command with the server's address, port number, and the client's name:

```sh
java GroupChatClient <host> <port> <name>
```

For example, if the server is running on `localhost` and port `50000`, and the client's name is `Alice`, use:

```sh
java GroupChatClient localhost 50000 Alice
```

## Testing the Group Chat

1. Start the server using the instructions above.
2. Open multiple terminal windows or command prompts.
3. In each terminal, start a client using the instructions above, providing a unique name for each client.
4. Type messages in any client terminal and observe that the messages are broadcast to all connected clients, showing the sender's name.

## Ending the Connection

To end the client or server, type `Ctrl-D` (on Windows `Ctrl-Z` followed by `Enter`). This is referred to as EOF (end of file), meaning end of input. The other side will also quit, indicating that the server/client closed the connection.

## Example

1. **Compile the Code:**

   ```sh
   javac GroupChatServer.java GroupChatClient.java
   ```

2. **Start the Server:**

   ```sh
   java GroupChatServer 50000
   ```

   Output:
   ```
   Waiting for clients to connect...
   ```

3. **Start Client 1:**

   ```sh
   java GroupChatClient localhost 50000 Alice
   ```

   Output:
   ```
   Connected to server at localhost:50000
   ```

4. **Start Client 2:**

   ```sh
   java GroupChatClient localhost 50000 Bob
   ```

   Output:
   ```
   Connected to server at localhost:50000
   ```

5. **Send Messages:**
   - In Client 1 terminal, type `Hello from Alice` and press Enter.
   - In Client 2 terminal, type `Hello from Bob` and press Enter.

   **Expected Output:**

   - **Client 1 Terminal:**
     ```
     Connected to server at localhost:50000
     Bob: Hello from Bob
     ```

   - **Client 2 Terminal:**
     ```
     Connected to server at localhost:50000
     Alice: Hello from Alice
     ```

By following these steps, you can test the group chat functionality and verify that messages are correctly broadcast to all connected clients, showing the sender's name.