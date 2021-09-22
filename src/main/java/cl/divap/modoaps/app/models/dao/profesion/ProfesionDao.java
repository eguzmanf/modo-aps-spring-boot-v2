package cl.divap.modoaps.app.models.dao.profesion;

import cl.divap.modoaps.app.models.entity.Profesion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProfesionDao implements IProfesionDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Profesion> findAll() {
        //
        return em.createQuery("from Profesion order by profesion").getResultList();
    }
}
