package cl.divap.modoaps.app.models.dao.usuario;

import cl.divap.modoaps.app.models.entity.RolePerfil;
import cl.divap.modoaps.app.models.entity.Usuario;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CustomUsuarioDao implements ICustomUsuarioDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public Usuario ifExistUsuarioServicio(Usuario usuario) {
        return em.createQuery("from Usuario u where u.servicioSalud.id = ?1 and u.rolePerfil.id = ?2 and u.enabled = ?3", Usuario.class)
            .setParameter(1, usuario.getServicioSalud().getId())
            .setParameter(2, 3L)     // ROLE_SERVICIO
            .setParameter(3, true)
            .getResultList().stream().findFirst().orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario ifExistUsuarioComuna(Usuario usuario) {
        return em.createQuery("from Usuario u where u.comuna.codigoComuna = ?1 and u.rolePerfil.id = ?2 and u.enabled = ?3", Usuario.class)
                .setParameter(1, usuario.getComuna().getCodigoComuna())
                .setParameter(2, 4L)     // ROLE_COMUNA
                .setParameter(3, true)
                .getResultList().stream().findFirst().orElse(null);
    }
}
