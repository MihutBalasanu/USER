package bankapp;

import dao.UserDao;
import model.User;

public class Login {

    private UserDao userDAO;

    public Login(){
        userDAO = new UserDao();
    }

    public User login(String username, String password) {
        User user = userDAO.findByUsernameAndPassword(username, password);
        return user;
    }
}
