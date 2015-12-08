package ch.bfh.projekt1.vatra;

import ch.bfh.projekt1.vatra.configuration.ApplicationSecurity;
import ch.bfh.projekt1.vatra.model.Algorithm;
import ch.bfh.projekt1.vatra.model.App;
import ch.bfh.projekt1.vatra.model.User;
import ch.bfh.projekt1.vatra.service.AlgorithmRepository;
import ch.bfh.projekt1.vatra.service.AppAlgorithmResultRepository;
import ch.bfh.projekt1.vatra.service.AppRepository;
import ch.bfh.projekt1.vatra.service.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dave on 23.10.15.
 */
@ComponentScan
@Configuration
@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new ApplicationSecurity();
    }


    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository,
    		AppRepository appRepository,
    		AlgorithmRepository algorithmRepository,
    		AppAlgorithmResultRepository appAlgorithmResultRepository) {
        return (args) -> {

            User userDave = userRepository.save(new User("Dave Wiedmer", "david.wiedmer@gmail.com", "test1234"));
            User userMichael = userRepository.save(new User("Michael Räss", "raess.michael@gmail.com", "Aa123456"));
            User userTobias = userRepository.save(new User("Tobias Schmoker", "tobischmoker@gmail.com", "zebra1234"));

            Algorithm algo1 = new Algorithm("Schnelle aufeinanderfolgende Zahlungen");
            Algorithm algo2 = new Algorithm("Zahlungen aus dem Ausland");
            Algorithm algo3 = new Algorithm("Verdächtige Zahlung");
            algorithmRepository.save(algo1);
            algorithmRepository.save(algo2);
            algorithmRepository.save(algo3); // Not assigned to app
            Set<Algorithm> algorithms = new HashSet<>();
            algorithms.add(algo1);
            algorithms.add(algo2);
            
            appRepository.save(new App("App Dave", "", 10, userDave, new Date(), new Date(), algorithms));
            appRepository.save(new App("App Michael", "", 10, userMichael, new Date(), new Date(), algorithms));
            appRepository.save(new App("App Tobias", "", 10, userTobias, new Date(), new Date(), algorithms));
            
            userRepository.findAll().forEach(user -> log.info(user.toString()));
        };
    }
}
