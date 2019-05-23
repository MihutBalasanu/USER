package repository;

import model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends CrudRepository< User,Long> {

    @Query(value = "SELECT u FROM User u where u.username = ?1 and u.password = ?2 ")
    Optional login(String username,String password);
    Optional findByToken(String token);

}
