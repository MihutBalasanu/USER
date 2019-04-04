package users;


import utils.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserFileReader {


    private static List<User> users = new ArrayList<>();
    private User user = new User();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());
    private static UserFileReader userFileReader;

    private UserFileReader() {

    }

    public static UserFileReader getInstance() {
        if (userFileReader == null) {
            userFileReader = new UserFileReader();
        }
        return userFileReader;
    }


    public List<User> getUsers(String path) {

        List<String> lines = FileReader.readFromFile(path);
        int numberOfLines = 1;
        for(String line : lines){
            String[] parts = line.split(" ");
            if(parts.length >= 2){
                String username = parts[0];
                String password = parts[1];
                user.setUsername(username);
                user.setPassword(password);
                users.add(user);
            }else{
                System.out.println("Wrong information at line " + numberOfLines + " !");
                LOGGER.warning("Wrong information at line " + numberOfLines + " !");
            }
            numberOfLines++;
        }
        return users;
    }
}
