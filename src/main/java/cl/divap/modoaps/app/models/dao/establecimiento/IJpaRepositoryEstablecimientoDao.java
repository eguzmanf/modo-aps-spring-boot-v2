package cl.divap.modoaps.app.models.dao.establecimiento;

import cl.divap.modoaps.app.models.entity.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IJpaRepositoryEstablecimientoDao extends JpaRepository<Establecimiento, Long> {

    @Query("SELECT DISTINCT e.idTipo, e.idTipoEst, e.tipoEstablecimiento FROM Establecimiento e")
    public List<Object> findDistinctTipoEstablecimiento();

    public Establecimiento findByCodigoNuevo(Integer codigoNuevo);

}
