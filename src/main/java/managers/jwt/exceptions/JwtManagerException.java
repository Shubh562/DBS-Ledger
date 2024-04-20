package managers.jwt.exceptions;

import lombok.Getter;
import managers.jwt.enums.JwtErrorCause;

@Getter
public class JwtManagerException extends RuntimeException {
    private final JwtErrorCause errorCause;

    public JwtManagerException(JwtErrorCause jwtErrorCause) {
        this(jwtErrorCause, null);
    }

    public JwtManagerException(JwtErrorCause jwtErrorCause, Throwable cause) {
        super(jwtErrorCause.name(), cause);
        this.errorCause = jwtErrorCause;
    }
}
