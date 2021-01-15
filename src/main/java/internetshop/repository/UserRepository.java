package internetshop.repository;

import internetshop.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select count (u.name) from User u where u.name = ?1")
    int countName(String userName);
    Optional<User> findByName(String name);
}
