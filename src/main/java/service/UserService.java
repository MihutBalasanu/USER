package service;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

@Service("userService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id){
        userRepository.delete(id);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }
}
