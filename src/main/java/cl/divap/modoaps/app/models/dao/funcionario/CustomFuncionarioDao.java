package cl.divap.modoaps.app.models.dao.funcionario;

import cl.divap.modoaps.app.models.entity.Funcionario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
public class CustomFuncionarioDao implements ICustomFuncionarioDao {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Funcionario> findAllCriteriaApiFuncionarios(Pageable pageable, HttpSession session) {
        //
        Map params = (Map) session.getAttribute("params");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Funcionario> cq = cb.createQuery(Funcionario.class);

        Root<Funcionario> funcionario = cq.from(Funcionario.class);
        cq.select(funcionario);

        List<Predicate> predicates = new ArrayList<>();

        if(params.get("run") != "" || params.get("run") != null) {
            predicates.add(cb.like(funcionario.get("run"),params.get("run") + "%"));
        }

        if(params.get("nombres") != "" || params.get("nombres") != null) {
            predicates.add(cb.like(funcionario.get("nombres"), "%" + params.get("nombres") + "%"));
        }

        if(params.get("apellidoPaterno") != "" || params.get("apellidoPaterno") != null) {
            predicates.add(cb.like(funcionario.get("apellidoPaterno"),"%" + params.get("apellidoPaterno") + "%"));
        }

        if(params.get("apellidoMaterno") != "" || params.get("apellidoMaterno") != null) {
            predicates.add(cb.like(funcionario.get("apellidoMaterno"),"%" + params.get("apellidoMaterno") + "%"));
        }

        if(params.get("sexo") != null) {
            predicates.add(cb.equal(funcionario.get("sexo").get("id"), params.get("sexo")));
        }

        if(params.get("nacionalidad") != null) {
            predicates.add(cb.equal(funcionario.get("nacionalidad").get("id"), params.get("nacionalidad")));
        }

        if(params.get("enabled").equals("1")) {
            predicates.add(cb.equal(funcionario.get("enabled"), true));
        } else if(params.get("enabled").equals("2")) {
            predicates.add(cb.equal(funcionario.get("enabled"), false));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Funcionario> query = em.createQuery(cq);

        int totalRows = query.getResultList().size();

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        Page<Funcionario> result = new PageImpl<Funcionario>(query.getResultList(), pageable, totalRows);

        return result;
    }

    @Override
    public void updateContratosEnabledFalse(Long id) {
        em.createNativeQuery("UPDATE contratos"
                + " SET enabled = :value"
                + " WHERE funcionario_id = :funcionarioId"
        )
                .setParameter("value", false)
                .setParameter("funcionarioId", id)
                .executeUpdate();
    }
}
