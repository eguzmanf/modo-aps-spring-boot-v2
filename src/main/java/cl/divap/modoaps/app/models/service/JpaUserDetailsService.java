package cl.divap.modoaps.app.models.service;

import cl.divap.modoaps.app.auth.handler.AuthenticationSuccessErrorHandler;
import cl.divap.modoaps.app.models.dao.usuario.IUsuarioDao;
import cl.divap.modoaps.app.models.entity.Role;
import cl.divap.modoaps.app.models.entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //
        Usuario usuario = usuarioDao.findByUsername(username);

        if(usuario == null) {
            //
            logger.error("Error en el Login: No existe el usuario '" + username + "' en el sistema");
            throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema");
        }

        /*////////////////////////////////////////////[ Login Attempts ]//////////////////////////////////////////////// */
        //
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String passwordLogin = request.getParameter("password");
        String passwordDataBase = usuario.getPassword();
        Boolean comparePassword	= passwordEncoder.matches(passwordLogin, passwordDataBase);
        logger.info("PasswordLogin: " + passwordLogin);
        logger.info("PasswordDataBase: " + passwordDataBase);
        logger.info("ComparePassword: " + comparePassword);

        String userLoging = username.toString().toLowerCase().trim();
        String userDataBase = usuario.getUsername().toString().toLowerCase().trim();
        logger.info("userLoging: ".concat(userLoging));
        logger.info("userDataBase: ".concat(userDataBase));

        if(userLoging.equals(userDataBase) && comparePassword == true && usuario.getLockoutEnd() != null && usuario.getEnabled() == false && usuario.getIntentos() >= AuthenticationSuccessErrorHandler.MAX_INTENTOS) {
            //
            Date fechaActual = new Date();
            Date fechaBloqueo = usuario.getLockoutEnd();

            if(fechaBloqueo.before(fechaActual)) {
                //
                usuario.setLockoutEnd(null);
                usuario.setEnabled(true);
                usuario.setIntentos(0);
                usuarioDao.save(usuario);
                logger.info("Usuario '" + username + "' ha sido habilitado nuevamente (desbloqueado) en el sistema");
            }
        }

        //
        /*//////////////////////////////////////////////////////////////////////////////////////////// */

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for(Role role: usuario.getRoles()) {
            //
            logger.info("Role: ".concat(role.getAuthority()));
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }

        if(authorities.isEmpty()) {
            //
            logger.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
            throw new UsernameNotFoundException("Error login: el usuario '" + username + "' no tiene roles asignados!");
        }

        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }

    /*//////////////////////////////////////////[ Login Attempts ]////////////////////////////////////////////////// */
    //
    public Usuario findByUsername(String username) {
        //
        return usuarioDao.findByUsername(username);
    }

    public Usuario save(Usuario usuario) {
        //
        return usuarioDao.save(usuario);
    }
    //
    /*//////////////////////////////////////////////////////////////////////////////////////////// */

}
