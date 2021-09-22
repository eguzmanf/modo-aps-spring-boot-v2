package cl.divap.modoaps.app.models.dao.comuna;

import cl.divap.modoaps.app.models.entity.Comuna;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ComunaDao implements IComunaDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Comuna> findAll() {
        return em.createQuery("from Comuna order by comuna").getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Comuna findByCodigoComuna(Long codigoComuna) {
        //
        return em.createQuery("from Comuna c where c.codigoComuna = ?1", Comuna.class)
                .setParameter(1, codigoComuna)
                .getSingleResult();
    }
}
