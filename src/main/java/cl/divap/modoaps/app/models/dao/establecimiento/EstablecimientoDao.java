package cl.divap.modoaps.app.models.dao.establecimiento;

import cl.divap.modoaps.app.models.entity.Establecimiento;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EstablecimientoDao implements IEstablecimientoDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Establecimiento> findAll() {
        return em.createQuery("from Establecimiento order by establecimientoNombre").getResultList();
    }
}