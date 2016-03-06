package ch.bfh.projekt1.vatra.configuration.csrf;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Hier wird der CsrfToken als Cookies gesetzt.
 * <p>
 * Informationen aus Tutorial: https://github.com/codesandnotes/secure-rest-spring-tut
 */
public class CsrfTokenResponseCookieBindingFilter extends OncePerRequestFilter {

    protected static final String REQUEST_ATTRIBUTE_NAME = "_csrf";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken token = (CsrfToken) request.getAttribute(REQUEST_ATTRIBUTE_NAME);

        Cookie cookie = new Cookie(CSRF.RESPONSE_COOKIE_NAME, token.getToken());
        cookie.setPath("/");

        response.addCookie(cookie);

        filterChain.doFilter(request, response);
    }
}
