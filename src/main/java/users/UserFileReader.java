package users;

import utils.Constants;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserFileReader {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private static List<User> users = new ArrayList<>();
    private static UserFileReader userFileReader;

    private UserFileReader() {
        readFromFile(Constants.USER_FILE_PATH);
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
            int numberOfLines = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String username = parts[0];
                    String password = parts[1];
                    User user = new User(username, password);
                    users.add(user);
                } else {
//                    System.out.println("Wrong information at line " + numberOfLines + " !");
                    LOGGER.warning("Wrong information at line " + numberOfLines + " !");
                }
                numberOfLines++;
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            LOGGER.warning(e.getMessage());

        } catch (IOException e) {
//            e.printStackTrace();
            LOGGER.warning(e.getMessage());
        }
        return users;
    }
}
