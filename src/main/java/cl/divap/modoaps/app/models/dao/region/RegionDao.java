package cl.divap.modoaps.app.models.dao.region;

import cl.divap.modoaps.app.models.entity.Region;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RegionDao implements IRegionDao{

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Region> findAll() {
        return em.createQuery("from Region order by region").getResultList();
    }
}
