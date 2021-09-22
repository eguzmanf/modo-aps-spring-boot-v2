package cl.divap.modoaps.app.validation;

import cl.divap.modoaps.app.models.dao.usuario.IUsuarioDao;
import cl.divap.modoaps.app.models.entity.Usuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ExisteEmailValidator implements Validator {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IUsuarioDao usuarioDao;

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //
        Usuario usuario = (Usuario) target;

        Usuario ifExistEmail = usuarioDao.findByEmail(usuario.getEmail());

        logger.info("Existe Email ???: " + ifExistEmail);
        logger.info("Existe Id ???: " + usuario.getId());

        // En crear Usuario (id=null)
        if(ifExistEmail != null && usuario.getId() == null) {
            errors.rejectValue("email", "Custom.existeemail.usuario.email");
        }
    }
}
