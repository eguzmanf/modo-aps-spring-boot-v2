package cl.divap.modoaps.app.models.dao.descuentoRetiro;

import cl.divap.modoaps.app.models.entity.DescuentoRetiro;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomDescuentoRetiroDao implements ICustomDescuentoRetiroDao {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DescuentoRetiro> findAllDescuentoRetiro() {
        return em.createQuery("SELECT d FROM DescuentoRetiro d").getResultList();
    }
}
