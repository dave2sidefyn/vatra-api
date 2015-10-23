package ch.bfh.projekt1.vatra;

import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by dave on 23.10.15.
 */
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {

            userRepository.save(new User("David Wiedmer", "david.wiedmer@gmail.com", "test1234"));

            userRepository.findAll().forEach(user -> log.info(user.toString()));
        };
    }


}
