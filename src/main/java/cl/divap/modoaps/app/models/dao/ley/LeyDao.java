package cl.divap.modoaps.app.models.dao.ley;

import cl.divap.modoaps.app.models.entity.Ley;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LeyDao implements ILeyDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Ley> findAll() {
        //
        return em.createQuery("from Ley order by ley").getResultList();
    }
}
