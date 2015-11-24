package ch.bfh.projekt1.vatra;

import ch.bfh.projekt1.vatra.configuration.ApplicationSecurity;
import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by dave on 23.10.15.
 */
@Configuration
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new ApplicationSecurity();
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, AppRepository appRepository) {
        return (args) -> {

            User userDave = userRepository.save(new User("Dave Wiedmer", "david.wiedmer@gmail.com", "test1234"));
            User userMichael = userRepository.save(new User("Michael Räss", "raess.michael@gmail.com", "Aa123456"));
            User userTobias = userRepository.save(new User("Tobias Schmoker", "tobischmoker@gmail.com", "zebra1234"));

            appRepository.save(new App("App Dave", userDave, new Date(), new Date(), new HashSet<>()));
            appRepository.save(new App("App Michael", userMichael, new Date(), new Date(), new HashSet<>()));
            appRepository.save(new App("App Tobias", userTobias, new Date(), new Date(), new HashSet<>()));


            userRepository.findAll().forEach(user -> log.info(user.toString()));
        };
    }
}
