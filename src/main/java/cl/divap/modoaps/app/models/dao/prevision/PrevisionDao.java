package cl.divap.modoaps.app.models.dao.prevision;

import cl.divap.modoaps.app.models.entity.Prevision;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PrevisionDao implements IPrevisionDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Prevision> findAll() {
        return em.createQuery("from Prevision order by prevision").getResultList();
    }
}
