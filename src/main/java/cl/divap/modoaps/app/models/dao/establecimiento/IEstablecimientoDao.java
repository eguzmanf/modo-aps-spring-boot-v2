package cl.divap.modoaps.app.models.dao.establecimiento;

import cl.divap.modoaps.app.models.entity.Comuna;
import cl.divap.modoaps.app.models.entity.Establecimiento;

import java.util.List;

public interface IEstablecimientoDao {

    public List<Establecimiento> findAll();

    public Establecimiento findByCodigoNuevo(Integer codigoNuevo);

    public List<Establecimiento> findEstablecimientoByIdComuna(Long id);
}
