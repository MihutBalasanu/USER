package users;


import utils.Constants;
import utils.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Reads the list of the users from the "users.txt" file.
 * @author Mihut Balasanu
 */
public class UserFileReader {


    private static List<User> users = new ArrayList<>();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private FileReader fileReader = new FileReader();

    /**
     * Every instance creation determines initialization of the users list.
     */
    public UserFileReader() {
        initializeUsersList();
    }

    /**
     * Gets the list of the users from the "users.txt" file.
     * @return List of the users accepted as valid from "users.txt" file
     */
    public static List<User> getUsers() {
        return users;
    }

    /**
     * Reads the list of the users from the "users.txt" file.
     */
    public void initializeUsersList() {

        // Set the list of lines read from the file
        List<String> lines = fileReader.readFromFile(Constants.USER_FILE_PATH);
        // Every line is split in parts that form an array.
        int numberOfLines = 1;
        for(String line : lines){
            String[] parts = line.split(" ");
            // To every element of the array, according to his index, it is assigned a field of an User
            if(parts.length >= 2){
                String username = parts[0];
                String password = parts[1];
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                users.add(user);
            // The user is notified if the line is incomplete
            }else{
                LOGGER.warning("Wrong information at line " + numberOfLines + " !");
            }
            numberOfLines++;
        }
    }
}
