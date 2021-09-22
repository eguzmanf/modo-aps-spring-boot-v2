package cl.divap.modoaps.app.models.dao.asignacionChofer;

import cl.divap.modoaps.app.models.entity.AsignacionChofer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AsignacionChoferDao implements IAsignacionChoferDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<AsignacionChofer> findAll() {
        //
        return em.createQuery("from AsignacionChofer order by asignacionChofer").getResultList();
    }
}
