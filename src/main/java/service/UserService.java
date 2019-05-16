package service;

import dao.UserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService {

    @Autowired
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
