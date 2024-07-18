package cl.divap.modoaps.app.models.dao.establecimiento;

import cl.divap.modoaps.app.models.entity.Establecimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IEstablecimientoDao {

    public List<Establecimiento> findAll();

    public Establecimiento findByCodigoNuevo(Integer codigoNuevo);

    public List<Establecimiento> findEstablecimientoByIdComuna(Long id);

    public Page<Establecimiento> findAllCriteriaApi(Pageable pageable);

    public List<Establecimiento> findTipoEstablecimientoUsingDistinct();

    public Page<Establecimiento> findAllEstablecimientoCriteriaApi(Pageable pageable, HttpSession session);
}
