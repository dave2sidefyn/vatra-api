package ch.bfh.projekt1.vatra;

import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.service.AppRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by dave on 23.10.15.
 */
@Configuration
@SpringBootApplication
public class Application {

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(AppRepository appRepository) {
        return (args) -> {

            User userDavidWiedmer = userService.create(new User("David Wiedmer", "david.wiedmer@gmail.com", "test1234"));
            User userZwei = userService.create(new User("Wiedmer", "dave@sidefyn.ch", "test1234"));

            appRepository.save(new App("Meine erste App", userDavidWiedmer, new Date(), new Date(), new HashSet<>()));
            appRepository.save(new App("Meine zweite App", userDavidWiedmer, new Date(), new Date(), new HashSet<>()));
            appRepository.save(new App("Meine eigene App", userZwei, new Date(), new Date(), new HashSet<>()));


            userService.findAll().forEach(user -> log.info(user.toString()));
        };
    }
}
