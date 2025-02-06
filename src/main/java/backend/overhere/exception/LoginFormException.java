package backend.overhere.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginFormException extends AuthenticationException {
    public LoginFormException(String msg) {
        super(msg);
    }

    public LoginFormException(String msg, Throwable t) {
        super(msg, t);
    }
}
