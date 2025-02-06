package backend.overhere.exception;

import org.springframework.security.core.AuthenticationException;

public class JsonFormException extends AuthenticationException {
    public JsonFormException(String msg) {
        super(msg);
    }

    public JsonFormException(String msg, Throwable t) {
        super(msg, t);
    }
}
