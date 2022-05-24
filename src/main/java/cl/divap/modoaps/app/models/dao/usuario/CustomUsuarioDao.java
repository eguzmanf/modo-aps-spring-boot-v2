package cl.divap.modoaps.app.models.dao.usuario;

import cl.divap.modoaps.app.models.entity.RolePerfil;
import cl.divap.modoaps.app.models.entity.Usuario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CustomUsuarioDao implements ICustomUsuarioDao {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Usuario ifExistUsuarioServicio(Usuario usuario) {
        return em.createQuery("from Usuario u where u.servicioSalud.id = ?1 and u.rolePerfil.id = ?2 and u.enabled = ?3", Usuario.class)
            .setParameter(1, usuario.getServicioSalud().getId())
            .setParameter(2, 3L)     // ROLE_SERVICIO
            .setParameter(3, true)
            .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Usuario ifExistUsuarioComuna(Usuario usuario) {
        return em.createQuery("from Usuario u where u.comuna.codigoComuna = ?1 and u.rolePerfil.id = ?2 and u.enabled = ?3", Usuario.class)
                .setParameter(1, usuario.getComuna().getCodigoComuna())
                .setParameter(2, 4L)     // ROLE_COMUNA
                .setParameter(3, true)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Usuario ifExistUsuarioServicioEdit(Usuario usuario) {
        return em.createQuery("from Usuario u where u.servicioSalud.id = ?1 and u.rolePerfil.id = ?2 and u.enabled = ?3 and u.username != ?4", Usuario.class)
                .setParameter(1, usuario.getServicioSalud().getId())
                .setParameter(2, 3L)     // ROLE_SERVICIO
                .setParameter(3, true)
                .setParameter(4, usuario.getUsername())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Usuario ifExistUsuarioComunaEdit(Usuario usuario) {
        return em.createQuery("from Usuario u where u.comuna.codigoComuna = ?1 and u.rolePerfil.id = ?2 and u.enabled = ?3 and u.username != ?4", Usuario.class)
                .setParameter(1, usuario.getComuna().getCodigoComuna())
                .setParameter(2, 4L)     // ROLE_COMUNA
                .setParameter(3, true)
                .setParameter(4, usuario.getUsername())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void updatePasswordByIdUsuario(Usuario usuario) {
        //
        em.createNativeQuery("UPDATE users u"
                + " SET u.password = :pass"
                + " WHERE u.id = :usuarioid"
        )
                .setParameter("pass", usuario.getPassword())
                .setParameter("usuarioid", usuario.getId())
                .executeUpdate();
    }

    @Override
    public List<Usuario> findAllCriteriaApi() {
        //
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);

        Root<Usuario> user = cq.from(Usuario.class);
        cq.select(user);

        TypedQuery<Usuario> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Page<Usuario> findAllCriteriaApi(Pageable pageable, HttpSession session) {
        //
        Map params = (Map) session.getAttribute("params");
        logger.info("Map params from Dao: " + params);
        logger.info("Map param run from Dao: " + params.get("run"));
        logger.info("Map param nombres from Dao: " + params.get("nombres"));
        logger.info("Map param apellidoPaterno from Dao: " + params.get("apellidoPaterno"));
        logger.info("Map param apellidoMaterno from Dao: " + params.get("apellidoMaterno"));
        logger.info("Map param sexo from Dao: " + params.get("sexo"));
        logger.info("Map param nacionalidad from Dao: " + params.get("nacionalidad"));
        logger.info("Map param Servicio de Salud from Dao: " + params.get("servicioSalud"));
        logger.info("Map param Comuna from Dao: " + params.get("comuna"));
        logger.info("Map param Role Perfil from Dao: " + params.get("rolePerfil"));
        logger.info("Map param Habilitado from Dao: " + params.get("enabled"));

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> cq = cb.createQuery(Usuario.class);

        Root<Usuario> user = cq.from(Usuario.class);
        cq.select(user);

        List<Predicate> predicates = new ArrayList<>();

        if(params.get("run") != "" || params.get("run") != null) {
            predicates.add(cb.like(user.get("run"),params.get("run") + "%"));
        }

        if(params.get("nombres") != "" || params.get("nombres") != null) {
            predicates.add(cb.like(user.get("nombre"),params.get("nombres") + "%"));
        }

        if(params.get("apellidoPaterno") != "" || params.get("apellidoPaterno") != null) {
            predicates.add(cb.like(user.get("apellidoPaterno"),params.get("apellidoPaterno") + "%"));
        }

        if(params.get("apellidoMaterno") != "" || params.get("apellidoMaterno") != null) {
            predicates.add(cb.like(user.get("apellidoMaterno"),params.get("apellidoMaterno") + "%"));
        }

        if(params.get("sexo") != null) {
            predicates.add(cb.equal(user.get("sexo").get("id"), params.get("sexo")));
        }

        if(params.get("nacionalidad") != null) {
            predicates.add(cb.equal(user.get("nacionalidad").get("id"), params.get("nacionalidad")));
        }

        if(params.get("servicioSalud") != null) {
            predicates.add(cb.equal(user.get("servicioSalud").get("id"), params.get("servicioSalud")));
        }

        if(params.get("comuna") != null) {
            predicates.add(cb.equal(user.get("comuna").get("codigoComuna"), params.get("comuna")));
        }

        if(params.get("rolePerfil") != null) {
            predicates.add(cb.equal(user.get("rolePerfil").get("id"), params.get("rolePerfil")));
        }

        if(params.get("enabled").equals("1")) {
            predicates.add(cb.equal(user.get("enabled"), true));
        } else if(params.get("enabled").equals("2")) {
            predicates.add(cb.equal(user.get("enabled"), false));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Usuario> query = em.createQuery(cq);

        int totalRows = query.getResultList().size();

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        Page<Usuario> result = new PageImpl<Usuario>(query.getResultList(), pageable, totalRows);

        return result;
    }

    @Override
    public Object findServicioIdByUserName(String username) {
        return em.createQuery("SELECT u.servicioSalud.id FROM Usuario u WHERE u.username = ?1")
                .setParameter(1, username)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object findComunaIdByUserName(String username) {
        return em.createQuery("SELECT u.comuna.codigoComuna FROM Usuario u WHERE u.username = ?1")
                .setParameter(1, username)
                .getResultList().stream().findFirst().orElse(null);
    }
}
