package cl.divap.modoaps.app.validation;

import cl.divap.modoaps.app.models.entity.Contrato;
import cl.divap.modoaps.app.models.service.IFuncionarioService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class JornadaLaboralLey44Horas implements Validator {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private IFuncionarioService funcionarioService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Contrato.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //
        Contrato contrato = (Contrato) target;

        logger.info("#####################################################:::::::::::::getJornadaLaboral: " + contrato.getJornadaLaboral());
        logger.info("#####################################################:::::::::::::getId: " + contrato.getId());

        String run = contrato.getFuncionario().getRun();

        Long jornadaLaboralLong = 0L;
        if(contrato.getJornadaLaboral() == null) {
            errors.rejectValue("jornadaLaboral", "Custom.jornada.contrato.jornadaLaboral.null");
        } else {
            Integer jornadaLaboralInteger = contrato.getJornadaLaboral();
            jornadaLaboralLong = Long.valueOf(jornadaLaboralInteger);
            logger.info("jornadaLaboralLong: " + jornadaLaboralLong);
        }

        Long totalJornadaLong = 0L;
        Integer jornadaLaboralDBInt = 0;
        Long countJornadaLaboraLong = 0L;
        if(contrato.getId() == null) {
            // crear
            Object countJornadaLaboraObject = funcionarioService.getCountJornadaLaboralByRutFuncionario(run);

            if(countJornadaLaboraObject != null) {
                countJornadaLaboraLong = Long.parseLong(countJornadaLaboraObject.toString());
            } else {
                countJornadaLaboraLong = 0L;
            }

            totalJornadaLong = countJornadaLaboraLong + jornadaLaboralLong;
            logger.info("totalJornadaLong: " + totalJornadaLong);
        } else {
            // editar
            Object jornadaLaboralDB = funcionarioService.getJornadaLaboralByIdContrato(contrato.getId());
            Long jornadaLaboralDBLong = Long.parseLong(jornadaLaboralDB.toString());
            jornadaLaboralDBInt = jornadaLaboralDBLong.intValue();

            funcionarioService.updateJornadaLaboralByIdContrato(contrato.getId(), 0);

            Object countJornadaLaboraEdit = funcionarioService.getCountJornadaLaboralByRutFuncionario(run);
            Long countJornadaLaboraLongEdit = Long.parseLong(countJornadaLaboraEdit.toString());
            totalJornadaLong = Math.abs(countJornadaLaboraLongEdit + jornadaLaboralLong);
        }

        if(contrato.getLey().getId() != null && (contrato.getLey().getId() == 1 || contrato.getLey().getId() == 3) && (totalJornadaLong > 44 || totalJornadaLong < 1)) {
            funcionarioService.updateJornadaLaboralByIdContrato(contrato.getId(), jornadaLaboralDBInt);
            errors.rejectValue("jornadaLaboral", "Custom.jornada.contrato.jornadaLaboral.error");
        }

    }
}
