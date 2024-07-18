import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        // Reading names from the input file
        ArrayList<String> names = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            while (scanner.hasNextLine()) {
                names.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
            e.printStackTrace();
            return;
        }

        // Connecting to the server and sending the names
        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // Send the list of names to the server
            oos.writeObject(names);
            oos.flush();

            // Read the sorted list of names from the server
            ArrayList<String> sortedNames = (ArrayList<String>) ois.readObject();
            System.out.println("Received sorted names: " + sortedNames);

            // Writing the sorted names to the output file
            try (PrintWriter writer = new PrintWriter(new File("sorted_names.txt"))) {
                for (String name : sortedNames) {
                    writer.println(name);
                }
            } catch (FileNotFoundException e) {
                System.out.println("Error writing to output file.");
                e.printStackTrace();
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}