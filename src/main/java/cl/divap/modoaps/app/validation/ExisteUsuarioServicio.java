package cl.divap.modoaps.app.validation;

import cl.divap.modoaps.app.models.entity.Usuario;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ExisteUsuarioServicio implements Validator {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Usuario.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //
        Usuario usuario = (Usuario) target;

        logger.info("################################[ Servicio ]####################################");
        logger.info("Existe Usuario Id ???: " + usuario.getId());
        logger.info("Existe Role Perfil Id ???: " + usuario.getRolePerfil().getId());
        logger.info("Existe Servicio Salud Id ???: " + usuario.getServicioSalud().getId());

        if(usuario.getRolePerfil().getId() != null && usuario.getRolePerfil().getId() == 2) {  // ROLE_SERVICIO
            //
            if(usuario.getId() == null) {   // Crear
                //
                Usuario ifExistUsuarioServicio = funcionarioService.ifExistUsuarioServicio(usuario);

                logger.info("Existe Usuario Servicio ???: " + ifExistUsuarioServicio);
                logger.info("################################[ - ]####################################");

                if (ifExistUsuarioServicio != null) {
                    //
                    errors.rejectValue("servicioSalud.id", "Custom.usuarioservicio.usuario.servicioSalud");
                }
            } else if(usuario.getId() != null && usuario.getId() > 0) {    // Editar
                //
                Usuario ifExistUsuarioServicioEdit = funcionarioService.ifExistUsuarioServicioEdit(usuario);

                logger.info("Existe Usuario Servicio en Editar ???: " + ifExistUsuarioServicioEdit);
                logger.info("################################[ - ]####################################");

                if (ifExistUsuarioServicioEdit != null) {
                    //
                    errors.rejectValue("servicioSalud.id", "Custom.usuarioservicio.usuario.servicioSalud");
                }
            }
        }
    }
}
