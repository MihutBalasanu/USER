package dto.converter;

import dto.UserDTO;
import model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User convertFromUserDTO(UserDTO userDto) {
        return new User(userDto.getName(), null);
    }

    public UserDTO convertToUserDTO(User user) {
        return new UserDTO(user.getUsername());
    }

}
