package cl.divap.modoaps.app.models.dao.role;

import cl.divap.modoaps.app.models.entity.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class RoleDao implements IRoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void updateRoleByIdUsuario(Usuario usuario) {

        em.createNativeQuery("UPDATE authorities"
            + " SET authority = :auth"
            + " WHERE user_id = :usuarioid"
        )
        .setParameter("auth", usuario.getRolePerfil().getRolePerfil())
        .setParameter("usuarioid", usuario.getId())
        .executeUpdate();
    }
}
