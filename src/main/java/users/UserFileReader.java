package users;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserFileReader {

    private static List<User> users = new ArrayList<>();
    private static UserFileReader userFileReader;

    private UserFileReader() {

    }

    public static UserFileReader getInstance() {
        if (userFileReader == null) {
            userFileReader = new UserFileReader();
        }
        return userFileReader;
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<User> readFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String username = parts[0];
                    String password = parts[1];
                    User user = new User(username, password);
                    users.add(user);
                } else {
                    System.out.println("Wrong information at line " + line + " !");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
