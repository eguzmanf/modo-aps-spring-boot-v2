package cl.divap.modoaps.app.models.dao.sexo;

import cl.divap.modoaps.app.models.entity.Sexo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SexoDao implements ISexoDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Sexo> findAll() {
        //
        return em.createQuery("from Sexo order by sexo").getResultList();
    }
}
