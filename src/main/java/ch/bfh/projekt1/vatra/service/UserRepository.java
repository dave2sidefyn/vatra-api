package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by David on 20.10.2015.
 */
@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(@Param("email") String email);

    User findByEmailAndPasswort(@Param("email") String email, @Param("passwort") String passwort);
}