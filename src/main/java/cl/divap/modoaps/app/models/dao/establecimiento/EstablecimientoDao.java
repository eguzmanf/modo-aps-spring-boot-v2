package cl.divap.modoaps.app.models.dao.establecimiento;

import cl.divap.modoaps.app.models.entity.Establecimiento;
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
import javax.persistence.criteria.Root;
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
}
