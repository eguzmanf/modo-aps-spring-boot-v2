package cl.divap.modoaps.app.models.dao.bienios;

import cl.divap.modoaps.app.models.entity.Bienios;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BieniosDao implements IBieniosDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Bienios> findAll() {
        //
        return em.createQuery("from Bienios order by length(bienios) asc").getResultList();
    }
}
