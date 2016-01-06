package ch.bfh.projekt1.vatra.configuration.cors;

import ch.bfh.projekt1.vatra.configuration.csrf.CSRF;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CORSFilter implements Filter {

	// This is to be replaced with a list of domains allowed to access the server
	private final List<String> allowedOrigins = Arrays.asList("http://localhost:63342", "http://localhost:9000");

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		// Lets make sure that we are working with HTTP (that is, against HttpServletRequest and HttpServletResponse objects)
		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;

			// Access-Control-Allow-Origin
			String origin = request.getHeader("Origin");
			response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
			response.setHeader("Vary", "Origin");

			// Access-Control-Max-Age
			response.setHeader("Access-Control-Max-Age", "3600");

			// Access-Control-Allow-Credentials
			response.setHeader("Access-Control-Allow-Credentials", "true");

			// Access-Control-Allow-Methods
			response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

			// Access-Control-Allow-Headers
			response.setHeader("Access-Control-Allow-Headers",
					"Origin, X-Requested-With, Content-Type, Accept, " + CSRF.REQUEST_HEADER_NAME);
		}

		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {
	}
}
