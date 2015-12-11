package yourwebproject2.unusedspringsecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: kameshr
 */
@Component
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static Logger LOG = LoggerFactory.getLogger(RESTAuthenticationEntryPoint.class);

    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        LOG.debug("<--- Inside authentication entry point --->");
        // this is very important for a REST based API login.
        // WWW-Authenticate header should be set as FormBased , else browser will show login dialog with realm
        response.setHeader("WWW-Authenticate", "FormBased");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
