package ch.bfh.projekt1.vatra.configuration;


import ch.bfh.projekt1.vatra.configuration.cors.CORSFilter;
import ch.bfh.projekt1.vatra.configuration.rest.RESTAuthenticationEntryPoint;
import ch.bfh.projekt1.vatra.configuration.rest.RESTAuthenticationSuccessHandler;
import ch.bfh.projekt1.vatra.configuration.rest.RESTLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Standard Security Handler für Spring Boot.
 * <p>
 * Informationen aus Tutorial: https://github.com/codesandnotes/secure-rest-spring-tut
 */
@Configuration
public class SecurityConfiguration {

    @Bean
    public RESTAuthenticationEntryPoint authenticationEntryPoint() {
        return new RESTAuthenticationEntryPoint();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public RESTAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new RESTAuthenticationSuccessHandler();
    }

    @Bean
    public CORSFilter corsFilter() {
        return new CORSFilter();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new RESTLogoutSuccessHandler();
    }
}
