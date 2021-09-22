package cl.divap.modoaps.app.models.dao.tipoContrato;

import cl.divap.modoaps.app.models.entity.TipoContrato;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TipoContratoDao  implements ITipoContratoDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<TipoContrato> findAll() {
        //
        return em.createQuery("from TipoContrato order by tipoContrato").getResultList();
    }
}
