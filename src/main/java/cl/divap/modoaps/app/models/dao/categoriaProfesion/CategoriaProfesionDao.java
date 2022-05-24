package cl.divap.modoaps.app.models.dao.categoriaProfesion;

import cl.divap.modoaps.app.models.entity.CategoriaProfesion;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CategoriaProfesionDao implements ICategoriaProfesionDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<CategoriaProfesion> findAll() {

        return em.createQuery("from CategoriaProfesion order by categoriaProfesion").getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoriaProfesion> findCategoriaProfesionLey19378() {
        return em.createQuery("FROM CategoriaProfesion cp WHERE cp.id <> 7", CategoriaProfesion.class).getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CategoriaProfesion> findCategoriaProfesionLeyHonorariosCodigo() {
        return em.createQuery("FROM CategoriaProfesion cp WHERE cp.id = 7", CategoriaProfesion.class).getResultList();
    }
}
