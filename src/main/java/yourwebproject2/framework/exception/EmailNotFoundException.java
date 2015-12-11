package yourwebproject2.framework.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @author: kameshr
 */
public class EmailNotFoundException extends AuthenticationException {
    public EmailNotFoundException(String message) {
        super(message);
    }
}
