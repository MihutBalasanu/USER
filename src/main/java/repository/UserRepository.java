package repository;

import model.User;

public interface UserRepository extends ModelRepository<User> {

    User findByUsername(String username);
}
