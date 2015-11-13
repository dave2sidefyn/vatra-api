package ch.bfh.projekt1.vatra;

import ch.bfh.projekt1.vatra.model.User;

/**
 * Created by dave on 11.11.15.
 */
public interface UserService {
    /**
     * @param email
     * @return
     */
    User findByEmail(String email);


    /**
     * @return
     */
    Iterable<User> findAll();


    /**
     * @param user
     * @return
     */
    User create(User user);
}
