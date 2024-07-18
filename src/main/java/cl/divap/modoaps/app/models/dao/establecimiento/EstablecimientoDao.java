package cl.divap.modoaps.app.models.dao.establecimiento;

import cl.divap.modoaps.app.models.entity.Establecimiento;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.jpa.QueryHints;
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
import java.util.List;
import java.util.Map;

@Repository
public class EstablecimientoDao implements IEstablecimientoDao {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Establecimiento> findAll() {
        return em.createQuery("from Establecimiento order by establecimientoNombre").getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Establecimiento findByCodigoNuevo(Integer codigoNuevo) {
        return em.createQuery("from Establecimiento e where e.codigoNuevo = ?1", Establecimiento.class)
                .setParameter(1, codigoNuevo)
                .getSingleResult();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Establecimiento> findEstablecimientoByIdComuna(Long id) {
        return em.createQuery("SELECT e FROM Establecimiento e WHERE e.comuna.codigoComuna = ?1 ORDER BY e.establecimientoNombre", Establecimiento.class)
                .setParameter(1, id)
                .getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Establecimiento> findAllCriteriaApi(Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Establecimiento> cq = cb.createQuery(Establecimiento.class);
        Root<Establecimiento> establecimiento = cq.from(Establecimiento.class);
        cq.select(establecimiento);

        TypedQuery<Establecimiento> query = em.createQuery(cq);

        int totalRows = query.getResultList().size();

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        Page<Establecimiento> result = new PageImpl<Establecimiento>(query.getResultList(), pageable, totalRows);

        return result;
    }

    @Deprecated
    @Transactional(readOnly = true)
    @Override
    public List<Establecimiento> findTipoEstablecimientoUsingDistinct() {
        List<Establecimiento> tipoEstablecimientos = em.createQuery("SELECT DISTINCT e.idTipo FROM Establecimiento e", Establecimiento.class).setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false).getResultList();
        return tipoEstablecimientos;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Establecimiento> findAllEstablecimientoCriteriaApi(Pageable pageable, HttpSession session) {
        Map params = (Map) session.getAttribute("params");
        logger.info("Map params from Dao: " + params);
        logger.info("Map param Región from Dao: " + params.get("region"));
        logger.info("Map param Servicio de Salud from Dao: " + params.get("servicio"));
        logger.info("Map param Comuna from Dao: " + params.get("comuna"));
        logger.info("Map param Código Nuevo o Deis from Dao: " + params.get("codigoNuevo"));
        logger.info("Map param Nombre Establecimiento from Dao: " + params.get("establecimientoNombre"));
        logger.info("Map param Tipo Establecimiento from Dao: " + params.get("tipoEstablecimiento"));
        logger.info("Map param Tipo Establecimiento Text from Dao: " + params.get("tipoEstablecimientoText"));
        logger.info("Map param Enabled from Dao: " + params.get("enabled"));
        logger.info("Map param Región Text from Dao: " + params.get("regionText"));
        logger.info("Map param Servicio Text from Dao: " + params.get("servicioText"));
        logger.info("Map param Comuna Text from Dao: " + params.get("comunaText"));

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Establecimiento> cq = cb.createQuery(Establecimiento.class);

        Root<Establecimiento> establecimientos = cq.from(Establecimiento.class);
        cq.select(establecimientos);

        List<Predicate> predicates = new ArrayList<>();

        if(params.get("establecimientoNombre") != null) {
            predicates.add(cb.like(establecimientos.get("establecimientoNombre"),"%" + params.get("establecimientoNombre") + "%"));
        }

        if(params.get("codigoNuevo") != null) {
            predicates.add(cb.equal(establecimientos.get("codigoNuevo"), params.get("codigoNuevo")));
        }

        if(params.get("region") != null) {
            predicates.add(cb.equal(establecimientos.get("region").get("id"), params.get("region")));
        }

        if(params.get("servicio") != null) {
            predicates.add(cb.equal(establecimientos.get("servicio").get("id"), params.get("servicio")));
        }

        if(params.get("comuna") != null) {
            predicates.add(cb.equal(establecimientos.get("comuna").get("codigoComuna"), params.get("comuna")));
        }

        if(params.get("tipoEstablecimiento") != null) {
            predicates.add(cb.equal(establecimientos.get("idTipo"), params.get("tipoEstablecimiento")));
        }

        if(params.get("enabled") != null && params.get("enabled").equals("0")) {
            predicates.add(cb.equal(establecimientos.get("elimID"), params.get("enabled")));
        } else if(params.get("enabled") != null && params.get("enabled").equals("1")) {
            predicates.add(cb.equal(establecimientos.get("elimID"), params.get("enabled")));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Establecimiento> query = em.createQuery(cq);

        int totalRows = query.getResultList().size();

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        Page<Establecimiento> result = new PageImpl<Establecimiento>(query.getResultList(), pageable, totalRows);

        return result;
    }
}
