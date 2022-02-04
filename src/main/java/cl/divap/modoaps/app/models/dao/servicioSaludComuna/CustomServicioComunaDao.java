package cl.divap.modoaps.app.models.dao.servicioSaludComuna;

import cl.divap.modoaps.app.models.entity.ServicioComuna;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomServicioComunaDao implements ICustomServicioComunaDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public ServicioComuna findServicioComunaByServicioId(Long idServicio) {

        return em.createQuery("select s from ServicioComuna s where s.servicioSalud.id = ?1", ServicioComuna.class)
                .setParameter(1, idServicio)
                .getSingleResult();
                // .getResultList().stream().findFirst().orElse(null);

        /*
        return em.createNativeQuery("SELECT * FROM servicio_comuna WHERE servicio_salud_id = ?1")
                .setParameter(1, idServicio)
                // .getResultList().stream().findFirst().orElse(null);
                .getSingleResult();
        */
    }
}
