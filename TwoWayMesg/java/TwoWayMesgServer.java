/*
 * Implementation of a two-way message server in java
 */

// Package for I/O related stuff
import java.io.*;

// Package for socket related stuff
import java.net.*;

/*
 * This class does all the server's job
 * It receives the connection from client
 * and prints messages sent from the client
 * It also sends messages typed by the user to the client
 */
public class TwoWayMesgServer {
    /*
     * The server program starts from here
     */
    public static void main(String args[]) {
        // Server needs the port number to listen on
        if (args.length != 2) {
            System.out.println("usage: java TwoWayMesgServer <port> <server name>");
            System.exit(1);
        }

        // Get the port on which server should listen
        int serverPort = Integer.parseInt(args[0]);
        String serverName = args[1];

        // Be prepared to catch socket related exceptions
        try {
            // Create a server socket with the given port
            ServerSocket serverSock = new ServerSocket(serverPort);
            System.out.println("Waiting for a client ...");

            // Wait to receive a connection request
            Socket clientSock = serverSock.accept();
            System.out.println("Connected to a client at ('" +
                                                ((InetSocketAddress) clientSock.getRemoteSocketAddress()).getAddress().getHostAddress()
                                                + "', '" +
                                                ((InetSocketAddress) clientSock.getRemoteSocketAddress()).getPort()
                                                + "')"
                                                );

            // No other clients, close the server socket
            serverSock.close();

            // Prepare to read from client
            BufferedReader fromClientReader = new BufferedReader(
                    new InputStreamReader(clientSock.getInputStream()));

            // Prepare to write to client with auto flush on
            PrintWriter toClientWriter = new PrintWriter(clientSock.getOutputStream(), true);

            // Prepare to read from keyboard
            BufferedReader fromUserReader = new BufferedReader(
                    new InputStreamReader(System.in));

            // Keep serving the client
            while (true) {
                // Read a message from the client
                String messageFromClient = fromClientReader.readLine();

                // If we get null, it means client sent EOF
                if (messageFromClient == null) {
                    System.out.println("Client closed connection");
                    clientSock.close();
                    break;
                }

                // Display the message
                System.out.println(messageFromClient);

                // Read a line from the keyboard
                String messageToClient = fromUserReader.readLine();

                // If we get null, it means user is done
                if (messageToClient == null) {
                    System.out.println("Closing connection");
                    clientSock.close();
                    break;
                }

                // Send the line to the client
                toClientWriter.println(serverName + ": " + messageToClient);
            }
        }
        catch(Exception e) {
            // Print the exception message
            System.out.println(e);
        }
    }
}