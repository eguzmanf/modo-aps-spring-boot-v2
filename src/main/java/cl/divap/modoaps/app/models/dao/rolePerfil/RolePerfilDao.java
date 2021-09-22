package cl.divap.modoaps.app.models.dao.rolePerfil;

import cl.divap.modoaps.app.models.entity.Comuna;
import cl.divap.modoaps.app.models.entity.RolePerfil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Repository
public class RolePerfilDao implements IRolePerfilDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<RolePerfil> findAll() {
        //
        List<Long> ids = Arrays.asList(1L, 2L, 3L, 4L, 5L);
        return em.createQuery("from RolePerfil r where r.id IN :ids", RolePerfil.class)
                .setParameter("ids", ids)
                .getResultList();

    }

    @Transactional(readOnly = true)
    @Override
    public RolePerfil findById(Long id) {
        return em.createQuery("from RolePerfil r where r.id = ?1", RolePerfil.class)
                .setParameter(1, id)
                .getSingleResult();
    }
}
