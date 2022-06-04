import entity.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread extends Thread {
    private final Socket socket;
    private final SimpleServer simpleServer;
    private boolean runningClientThread = true;
    private boolean hasAccess = false;
    private String clientName;

    public ClientThread(Socket socket, SimpleServer simpleServer) {
        this.socket = socket;
        this.simpleServer = simpleServer;
    }

    public void run() {
        try {
            socket.setSoTimeout(30000); // implements a timeout for a connection (a number of minutes)
        } catch (SocketException e) {
            try {
                socket.close(); // If the server does not receive any command from a logged in client in the specified period of time,
                // it will terminate the connection
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        try {
            while (runningClientThread) {
                // Get the request from the input stream: client → server
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String request = in.readLine();

                // Send the response to the output stream: server → client
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                String raspuns = "Server received the request " + request;

                if (request.compareTo("stop") == 0) {
                    simpleServer.setRunning(false);
                    runningClientThread = false;
                } else if (request.compareTo("exit") == 0) {
                    runningClientThread = false;
                } else if (request.compareTo("register") == 0) {
                    String name = in.readLine();
                    String password = in.readLine();

                    Commands.register(new Person(name, password));

                    hasAccess = true;
                    clientName = name;
                } else if (request.compareTo("login") == 0) {
                    String name = in.readLine();
                    String password = in.readLine();

                    hasAccess = Commands.login(name, password);

                    if (hasAccess) {
                        raspuns = "Login successful";
                        clientName = name;
                    } else raspuns = "Login failed";

                } else if (request.compareTo("friend") == 0) {
                    String line = in.readLine();
                    String[] names = line.split(",");

                    if (hasAccess) {
                        if (Commands.friend(clientName, names)) {
                            raspuns = "Job done!";
                        } else raspuns = "At least one friend was not found";
                    } else {
                        raspuns = "Permission denied";
                    }
                } else if (request.compareTo("send") == 0) {
                    String text = in.readLine();

                    if (hasAccess) {
                        Commands.send(clientName, text);
                        raspuns = "Job done!";
                    } else raspuns = "Permission denied";
                } else if (request.compareTo("read") == 0) {
                    if (hasAccess) {
                        raspuns = Commands.read(clientName).toString();
                    } else raspuns = "Permission denied";
                }

                out.println(raspuns);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
