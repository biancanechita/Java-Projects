import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        String serverAddress = "127.0.0.1"; // The server's IP address

        int PORT = 8100; // The server's port

        try (Socket socket = new Socket(serverAddress, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String request = "";

            boolean running = true;

            while (running) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                System.out.print("Type here..");

                try {
                    request = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (request.compareTo("exit") == 0) {
                    running = false;
                }

                // Send a request to the server
                out.println(request);

                if (request.compareTo("register") == 0 || request.compareTo("login") == 0) {
                    System.out.print("Type name: ");

                    String name = null;
                    try {
                        name = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    out.println(name);

                    System.out.print("Type password: ");

                    String password = null;
                    try {
                        password = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    out.println(password);
                } else if (request.compareTo("friend") == 0) {
                    System.out.println("Use comma-separated values: ");

                    String names = null;
                    try {
                        names = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    out.println(names);
                } else if (request.compareTo("send") == 0) {
                    System.out.println("Type message: ");

                    String text = null;
                    try {
                        text = reader.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    out.println(text);
                } else if (request.compareTo("read") == 0) {
                    System.out.println("You have messages: ");
                }

                // Wait the response from the server ("Hello World!")
                if (request.compareTo("stop") != 0) {
                    String response = in.readLine();
                    System.out.println(response);
                } else running = false;
            }
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        } catch (ConnectException e) {
            System.err.println("No server listening... " + e);
        } catch (SocketException e) {
            System.err.println("Time Limit Exceeded..." + e);
        }
    }
}