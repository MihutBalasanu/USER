package users;


import utils.Constants;
import utils.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserFileReader {


    private static List<User> users = new ArrayList<>();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private static UserFileReader userFileReader;
    FileReader fileReader = new FileReader();

    private UserFileReader() {
        initializeUsersList();
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

    public void initializeUsersList() {

        List<String> lines = fileReader.readFromFile(Constants.USER_FILE_PATH);
        int numberOfLines = 1;
        for(String line : lines){
            String[] parts = line.split(" ");
            if(parts.length >= 2){
                String username = parts[0];
                String password = parts[1];
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                users.add(user);
            }else{
                LOGGER.warning("Wrong information at line " + numberOfLines + " !");
            }
            numberOfLines++;
        }
    }
}
