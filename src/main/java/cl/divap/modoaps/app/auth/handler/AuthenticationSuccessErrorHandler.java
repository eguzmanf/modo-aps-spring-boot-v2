package cl.divap.modoaps.app.auth.handler;

import cl.divap.modoaps.app.errors.UsuarioBloqueadoException;
import cl.divap.modoaps.app.models.entity.Usuario;
import cl.divap.modoaps.app.models.service.JpaUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher {

    public final static Integer MAX_INTENTOS = 3;
    public final static Integer MINUTOS = 1;

    private Logger logger = LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);

    @Autowired
    private JpaUserDetailsService usuarioService;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        //
        Usuario usuario = usuarioService.findByUsername(authentication.getName());

        String fechaNacimiento = usuario.getFechaNacimiento().toString();
        logger.info("Fecha Nacimiento String (Success): " + fechaNacimiento);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(fechaNacimiento);
            usuario.setFechaNacimiento(date);
            logger.info("Fecha Nacimiento Date (Success): " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(usuario.getIntentos() != null && usuario.getIntentos() > 0) {
            //
            usuario.setLockoutEnd(null);
            usuario.setIntentos(0);
            usuario.setEnabled(true);
            usuarioService.save(usuario);
        }
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        //
        Usuario usuario = usuarioService.findByUsername(authentication.getName());

        String fechaNacimiento = usuario.getFechaNacimiento().toString();
        logger.info("Fecha Nacimiento String (Failure): " + fechaNacimiento);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(fechaNacimiento);
            usuario.setFechaNacimiento(date);
            logger.info("Fecha Nacimiento Date (Failure): " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        if(usuario == null) {
            //
            logger.error("Error en el Login: No existe el usuario '" + authentication.getName() + "' en el sistema");
            throw new UsernameNotFoundException("Username: " + authentication.getName() + " no existe en el sistema!");
        }

        if (usuario.getIntentos() == null) {
            //
            usuario.setIntentos(0);
        }

        usuario.setIntentos(usuario.getIntentos() + 1);

        if(usuario.getIntentos() == MAX_INTENTOS) {
            //
            Date fechaActual = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaActual);
            calendar.add(Calendar.MINUTE, MINUTOS);
            Date fechaFutura = calendar.getTime();

            usuario.setEnabled(false);
            usuario.setLockoutEnd(fechaFutura);
            usuarioService.save(usuario);

            logger.error("Error en el Login: Usuario '" + authentication.getName() + "' ha sido bloqueado (deshabilitado) en el sistema");
            throw new UsuarioBloqueadoException("Error en el Login: Usuario '" + authentication.getName() + "' ha sido bloqueado (deshabilitado) en el sistema.");
        }

        usuarioService.save(usuario);
    }
}
