package cl.divap.modoaps.app.models.dao.servicioSalud;

import cl.divap.modoaps.app.models.entity.Comuna;
import cl.divap.modoaps.app.models.entity.ServicioSalud;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ServicioSaludDao implements IServicioSaludDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<ServicioSalud> findAll() {
        //
        return em.createQuery("from ServicioSalud order by servicioSalud").getResultList();
    }

    @Override
    public List<ServicioSalud> findServiciosLaGranja() {
        //
        return em.createQuery("SELECT s FROM ServicioSalud s WHERE s.id IN (13, 14)", ServicioSalud.class).getResultList();
    }

    @Override
    public ServicioSalud findServicioByServicioId(Long servicioId) {
        return em.createQuery("from ServicioSalud s where s.id = ?1", ServicioSalud.class)
                .setParameter(1, servicioId)
                .getSingleResult();
    }
}
