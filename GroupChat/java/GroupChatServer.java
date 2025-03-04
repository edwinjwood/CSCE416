/*
 * Implementation of a group chat server in Java
 */

// I/O related package
import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * This class does all of group chat server's job
 * It simultaneously watches both keyboard and socket for input
 *
 * It consists of 2 threads: parent thread (code inside init method)
 * and child thread (code inside run method)
 *
 * Parent thread spawns a child thread and then
 * reads from the socket and writes to the screen
 *
 * Child thread reads from the keyboard and writes to socket
 *
 * Since a thread is being created with this class object,
 * this class declaration includes "implements Runnable"
 */
public class GroupChatServer implements Runnable
{
    // For reading messages from the socket
    private BufferedReader fromSockReader;

    // For writing messages to the socket
    private PrintWriter toSockWriter;

    // Client's name
    private String clientName;

    // List of all connected clients
    private static CopyOnWriteArrayList<PrintWriter> clientWriters = new CopyOnWriteArrayList<>();

    // Constructor sets the reader and writer for the child thread
    public GroupChatServer(BufferedReader reader, PrintWriter writer, String clientName)
    {
        fromSockReader = reader;
        toSockWriter = writer;
        this.clientName = clientName;
        clientWriters.add(writer);
    }

    // The child thread starts here
    public void run()
    {
        // Read from the socket and write to other clients
        try {
            // Keep doing till user types EOF (Ctrl-D)
            while (true) {
                // Read a line from the socket
                String line = fromSockReader.readLine();

                // If we get null, it means EOF, so quit
                if (line == null) {
                    System.out.println("*** Client closed connection: " + clientName);
                    break;
                }

                // Broadcast the line to all clients
                String message = clientName + ": " + line;
                for (PrintWriter writer : clientWriters) {
                    if (writer != toSockWriter) {
                        writer.println(message);
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            clientWriters.remove(toSockWriter);
            System.out.println("*** Client disconnected: " + clientName);
            try {
                toSockWriter.close();
                fromSockReader.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    /*
     * The messaging server program starts from here.
     * It sets up streams for reading & writing from keyboard and socket
     * Spawns a thread which does the stuff under the run() method
     * Then, it continues to read from socket and write to display
     */
    public static void main(String args[])
    {
        // Server needs a port to listen on
        if (args.length != 1) {
            System.out.println("usage: java GroupChatServer <port>");
            System.exit(1);
        }

        // Get the port on which server should listen
        int serverPort = Integer.parseInt(args[0]);

        // Be prepared to catch socket related exceptions
        ServerSocket serverSocket = null;
        try {
            // Create a server socket with the given port
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Waiting for clients to connect...");

            while (true) {
                // Wait for a client and accept it
                Socket clientSock = serverSocket.accept();
                System.out.println("Connected to a client at ('" +
                                    ((InetSocketAddress) clientSock.getRemoteSocketAddress()).getAddress().getHostAddress()
                                    + "', '" +
                                    ((InetSocketAddress) clientSock.getRemoteSocketAddress()).getPort()
                                    + "')"
                                    );

                // Set up a thread to read from socket and send to other clients
                try {
                    // Prepare to write to socket with auto flush on
                    PrintWriter toSockWriter = new PrintWriter(clientSock.getOutputStream(), true);

                    // Prepare to read from socket
                    BufferedReader fromSockReader = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));

                    // Read the client's name
                    String clientName = fromSockReader.readLine();

                    // Spawn a thread to read from socket and write to other clients
                    Thread child = new Thread(new GroupChatServer(fromSockReader, toSockWriter, clientName));
                    child.start();
                }
                catch(Exception e) {
                    System.out.println(e);
                }
            }
        }
        catch(Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}