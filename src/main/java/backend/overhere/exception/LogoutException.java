package backend.overhere.exception;

import org.springframework.security.core.AuthenticationException;

public class LogoutException extends AuthenticationException {
    public LogoutException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public LogoutException(String msg) {
        super(msg);
    }
}
