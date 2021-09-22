package cl.divap.modoaps.app.auth.handler;

import cl.divap.modoaps.app.errors.UsuarioBloqueadoException;
import cl.divap.modoaps.app.models.dao.usuario.IUsuarioDao;
import cl.divap.modoaps.app.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailedHandler extends SimpleUrlAuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(LoginFailedHandler.class);

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //
        String username = request.getParameter("username");
        Usuario usuario = usuarioDao.findByUsername(username);

        SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
        FlashMap flashMap = new FlashMap();

        if(usuario == null) {
            String mensaje = "Error en Login: Usuario ó Contraseña incorrecta, por favor intentelo nuevamente!";
            flashMap.put("error", mensaje);
            logger.error(mensaje);
        }

        if (exception instanceof UsuarioBloqueadoException) {
            //
            String mensaje = "El Usuario: '" + username + "' se encuentra bloqueado hasta '" + usuario.getLockoutEnd() + "'";

            flashMap.put("warning", mensaje);

            logger.error(mensaje);

        } else if(exception instanceof BadCredentialsException){

            flashMap.put("error",  "Error en Login: Nombre de Usuario ó Contraseña incorrecta, por favor vuelva a intentarlo!!!");

        } else if(usuario != null && usuario.getEnabled() == false && usuario.getLockoutEnd() != null) {
            //
            // Date fechaInitDate = usuario.getLockoutEnd();
            flashMap.put("error", "El Usuario: '" + username + "' se encuentra bloqueado hasta '" + usuario.getLockoutEnd() + "'!");
        }

        flashMapManager.saveOutputFlashMap(flashMap, request, response);
        super.onAuthenticationFailure(request, response, exception);
    }
}
