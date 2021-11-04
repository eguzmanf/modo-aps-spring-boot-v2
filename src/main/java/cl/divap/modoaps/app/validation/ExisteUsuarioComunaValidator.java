package cl.divap.modoaps.app.validation;

import cl.divap.modoaps.app.models.entity.Usuario;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ExisteUsuarioComunaValidator implements Validator {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Usuario usuario = (Usuario) target;

        logger.info("################################[ Comuna ]####################################");
        logger.info("Existe Usuario Id ???: " + usuario.getId());
        logger.info("Existe Role Perfil Id ???: " + usuario.getRolePerfil().getId());
        logger.info("Existe Comuna Id ???: " + usuario.getComuna().getCodigoComuna());

        if(usuario.getRolePerfil().getId() != null && usuario.getRolePerfil().getId() == 4) {  // ROLE_COMUNA
            //
            if(usuario.getId() == null) {   // Crear
                //
                Usuario ifExistUsuarioComuna = funcionarioService.ifExistUsuarioComuna(usuario);

                logger.info("Existe Usuario Comuna ???: " + ifExistUsuarioComuna);
                logger.info("################################[ - ]####################################");

                if (ifExistUsuarioComuna != null) {
                    //
                    errors.rejectValue("comuna.codigoComuna", "Custom.usuariocomuna.usuario.comuna");
                }
            } else if(usuario.getId() != null && usuario.getId() > 0) {    // Editar
                //
                Usuario ifExistUsuarioComunaEdit = funcionarioService.ifExistUsuarioComunaEdit(usuario);

                logger.info("Existe Usuario Comuna en Editar???: " + ifExistUsuarioComunaEdit);
                logger.info("################################[ - ]####################################");

                if (ifExistUsuarioComunaEdit != null) {
                    //
                    errors.rejectValue("comuna.codigoComuna", "Custom.usuariocomuna.usuario.comuna");
                }
            }
        }
    }
}
