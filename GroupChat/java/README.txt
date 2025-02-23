# These instructions are for running the GroupChat application written in Java

# Compile the client and server programs
% javac GroupChatServer.java
% javac GroupChatClient.java

# First, run the server program with a port number (say 50000)
% java GroupChatServer 50000

# Then, run the client program with the server's whereabouts
% java GroupChatClient localhost 50000

# You can run multiple clients to simulate a group chat
# Each client can send messages that will be broadcast to all connected clients

# Type messages at any client and see them displayed at all other clients

# To end the client or server, type Ctrl-D (on Windows Ctrl-Z followed by return)
# This is referred to as EOF (end of file), meaning end of input
# Then, the other will also quit saying server/client closed the connection
