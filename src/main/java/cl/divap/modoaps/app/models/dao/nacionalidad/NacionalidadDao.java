package cl.divap.modoaps.app.models.dao.nacionalidad;

import cl.divap.modoaps.app.models.entity.Nacionalidad;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class NacionalidadDao implements INacionalidadDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Nacionalidad> findAll() {
        //
        return em.createQuery("from Nacionalidad order by nacionalidad").getResultList();
    }
}
