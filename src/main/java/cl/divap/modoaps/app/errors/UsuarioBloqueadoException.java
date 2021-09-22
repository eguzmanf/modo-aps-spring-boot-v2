package cl.divap.modoaps.app.errors;

import org.springframework.security.core.AuthenticationException;

public class UsuarioBloqueadoException extends AuthenticationException {

    private static final long serialVersionUID = 4809000717647140011L;

    public UsuarioBloqueadoException(String message) {
        super(message);
    }
}
