package cl.divap.modoaps.app.models.dao.especialidad;

import cl.divap.modoaps.app.models.entity.Especialidad;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class EspecialidadDao implements IEspecialidadDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Especialidad> findAll() {
        return em.createQuery("from Especialidad order by especialidad").getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Especialidad> findEspecialidadByProfesionId(Long profesionId) {
        return em.createQuery("FROM Especialidad e WHERE e.profesion.id = ?1", Especialidad.class)
                .setParameter(1, profesionId)
                .getResultList();
    }
}
