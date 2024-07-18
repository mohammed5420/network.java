import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started and waiting for clients...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    System.out.println("Client connected!");

                    // Read the list of names from the client
                    ArrayList<String> names = (ArrayList<String>) ois.readObject();
                    System.out.println("Received names: " + names);

                    // Sort the list of names
                    Collections.sort(names);
                    System.out.println("Sorted names: " + names);

                    // Send the sorted list back to the client
                    oos.writeObject(names);
                    oos.flush();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
