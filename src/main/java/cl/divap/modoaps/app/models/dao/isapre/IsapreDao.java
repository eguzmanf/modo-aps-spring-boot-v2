package cl.divap.modoaps.app.models.dao.isapre;

import cl.divap.modoaps.app.models.entity.Isapre;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class IsapreDao implements IIsapreDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Isapre> findAll() {
        return em.createQuery("from Isapre order by isapre").getResultList();
    }
}
