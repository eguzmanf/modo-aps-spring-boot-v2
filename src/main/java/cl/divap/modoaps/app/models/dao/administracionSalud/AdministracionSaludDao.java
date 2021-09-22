package cl.divap.modoaps.app.models.dao.administracionSalud;

import cl.divap.modoaps.app.models.entity.AdministracionSalud;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AdministracionSaludDao implements IAdministracionSaludDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<AdministracionSalud> findAll() {
        //
        return em.createQuery("from AdministracionSalud order by adminSalud").getResultList();
    }
}
