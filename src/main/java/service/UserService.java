package service;

import dao.UserDao;
import model.User;

public class UserService {

    private UserDao userDao = new UserDao();

    public User findById(Long id){
        return userDao.getEntityById(User.class, id);
    }

    public void createUser(User user) {
        userDao.createEntity(user);
    }

    public void deleteUser(User user){
        userDao.deleteEntity(user);
    }

    public void updateUser(User user){
        userDao.updateEntity(user);
    }
}
