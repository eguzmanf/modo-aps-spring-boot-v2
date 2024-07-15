package cl.divap.modoaps.app.validation;

import cl.divap.modoaps.app.models.dao.establecimiento.IJpaRepositoryEstablecimientoDao;
import cl.divap.modoaps.app.models.entity.Establecimiento;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ExisteEstablecimientoValidator implements Validator {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IJpaRepositoryEstablecimientoDao establecimientoDao;

    @Override
    public boolean supports(Class<?> clazz) {
        return Establecimiento.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Establecimiento establecimiento = (Establecimiento) target;

        Establecimiento ifExistEstablecimiento = establecimientoDao.findByCodigoNuevo(establecimiento.getCodigoNuevo());

        logger.info("Existe Establecimiento ???: " + ifExistEstablecimiento);
        logger.info("Existe Id ???: " + establecimiento.getId());
        logger.info("Existe CodigoNuevo ???: " + establecimiento.getCodigoNuevo());

        // Solo en crear Establecimiento (id=null)
        if(ifExistEstablecimiento != null && establecimiento.getId() == null) {
            errors.rejectValue("codigoNuevo", "Custom.existeestablecimiento.establecimiento.codigoNuevo");
        }
    }
}
