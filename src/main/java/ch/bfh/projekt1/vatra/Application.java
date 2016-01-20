package ch.bfh.projekt1.vatra;

import ch.bfh.projekt1.vatra.algorithm.AlgorithmEnum;
import ch.bfh.projekt1.vatra.configuration.ApplicationSecurity;
import ch.bfh.projekt1.vatra.model.*;
import ch.bfh.projekt1.vatra.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Startklasse, Hier wird das Projekt gestartet
 * <p>
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


    /**
     * Bereitstellung für Demodaten
     *
     * @param userRepository       wird benötigt um Users zu erstellen
     * @param appRepository        wird benötigt um Apps zu erstellen
     * @param algorithmRepository  wird benötigt um Algorithmen zu erstellen
     * @param whitelabelRepository wird benötigt um Whitlabels zu erstellen
     * @param requestRepository    wird benötigt um Request zu erstellen
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner demo(@Nonnull UserRepository userRepository,
                                  @Nonnull AppRepository appRepository,
                                  @Nonnull AlgorithmRepository algorithmRepository,
                                  @Nonnull WhitelabelRepository whitelabelRepository,
                                  @Nonnull RequestRepository requestRepository) {
        return (args) -> {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            User userDave = userRepository.save(new User("Dave Wiedmer", "david.wiedmer@gmail.com", bCryptPasswordEncoder.encode("test1234")));
            User userMichael = userRepository.save(new User("Michael Räss", "raess.michael@gmail.com", bCryptPasswordEncoder.encode("Aa123456")));
            User userTobias = userRepository.save(new User("Tobias Schmoker", "tobischmoker@gmail.com", bCryptPasswordEncoder.encode("zebra1234")));

            Algorithm algo1 = new Algorithm("Schnelle aufeinanderfolgende Zahlungen", AlgorithmEnum.ROBOTIC_ALGORITHM);
            Algorithm algo2 = new Algorithm("Zahlungsüberwachung nach Ortsangaben", AlgorithmEnum.GEO_ALGORITHM);
            Algorithm algo3 = new Algorithm("Kreditkartenvalidierung", AlgorithmEnum.CREDITCARD_ALGORITHM);
            Algorithm algo4 = new Algorithm("Honeypot", AlgorithmEnum.HONEYPOT_ALGORITHM);
            algorithmRepository.save(algo1);
            algorithmRepository.save(algo2);
            algorithmRepository.save(algo3);
            algorithmRepository.save(algo4);
            Set<Algorithm> algorithms = new HashSet<>();
            algorithms.add(algo1);
            algorithms.add(algo2);
            algorithms.add(algo3);
            algorithms.add(algo4);


            appRepository.save(new App("App Dave", 10, userDave, new Date(), new Date(), algorithms));
            appRepository.save(new App("App Michael", 10, userMichael, new Date(), new Date(), algorithms, "d33i7sn7gj62t4mdptsfe1pclt"));
            appRepository.save(new App("App Tobias", 10, userTobias, new Date(), new Date(), algorithms));

            appRepository.findAll().forEach(app -> {
                Whitelabel whitelabel = new Whitelabel("127.0.0.1:9000", app);
                whitelabelRepository.save(whitelabel);

                Request r1 = new Request("127.0.0.1:9000", app, "TODO: ClientInformation", true, new Date());
                Request r2 = new Request("127.0.0.1:9000", app, "TODO: ClientInformation", false, new Date());
                Request r3 = new Request("127.0.0.1:9000", app, "TODO: ClientInformation", false, new Date());
                Request r4 = new Request("127.0.0.1:9000", app, "TODO: ClientInformation", false, new Date());

                requestRepository.save(r1);
                requestRepository.save(r2);
                requestRepository.save(r3);
                requestRepository.save(r4);
            });

            userRepository.findAll().forEach(user -> log.info(user.toString()));
        };
    }
}
