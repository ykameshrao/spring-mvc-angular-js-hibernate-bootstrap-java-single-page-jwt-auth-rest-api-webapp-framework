package yourwebproject2.unusedspringsecurity.token;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: kameshr
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {
    private TokenAuthenticationService authenticationService;

    public StatelessAuthenticationFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Authentication authentication = authenticationService.getAuthentication(httpRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public TokenAuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public void setAuthenticationService(TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
}
