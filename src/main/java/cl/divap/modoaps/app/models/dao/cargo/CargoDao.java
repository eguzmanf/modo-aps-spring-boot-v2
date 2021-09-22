package cl.divap.modoaps.app.models.dao.cargo;

import cl.divap.modoaps.app.models.entity.Cargo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CargoDao implements ICargoDao{

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Cargo> findAll() {
        return em.createQuery("from Cargo order by cargo").getResultList();
    }
}
