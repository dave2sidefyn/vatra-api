package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by David on 20.10.2015.
 */
public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(@Param("email") String email);

    User findByEmailAndPasswort(@Param("email") String email, @Param("passwort") String passwort);
}
