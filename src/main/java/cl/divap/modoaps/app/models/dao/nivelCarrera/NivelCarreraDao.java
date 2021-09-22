package cl.divap.modoaps.app.models.dao.nivelCarrera;

import cl.divap.modoaps.app.models.entity.NivelCarrera;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class NivelCarreraDao implements INivelCarreraDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<NivelCarrera> findAll() {
        return em.createQuery("from NivelCarrera order by length(nivelCarrera) desc").getResultList();
    }
}
