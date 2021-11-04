package cl.divap.modoaps.app.models.dao.funcionario;

import cl.divap.modoaps.app.models.entity.Contrato;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CustomContratoDao implements ICustomContratoDao {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveContratoCustom(Contrato contrato) {
        //
        if(contrato.getId() != null && contrato.getId() > 0) {
            //
            logger.info("Ingresando en bucle editar (merge) en función saveContratoCustom");
            em.merge(contrato);

        } else {
            //
            logger.info("Ingresando en bucle crear (persist) en función saveContratoCustom");
            em.persist(contrato);

        }
    }
}
