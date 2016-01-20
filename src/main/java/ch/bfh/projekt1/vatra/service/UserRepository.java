package ch.bfh.projekt1.vatra.service;

import ch.bfh.projekt1.vatra.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.annotation.Nullable;

/**
 * Repository für Users
 * <p>
 * Created by David on 20.10.2015.
 */
public interface UserRepository extends CrudRepository<User, String> {

    @Nullable
    User findByEmail(@Param("email") String email);
}
