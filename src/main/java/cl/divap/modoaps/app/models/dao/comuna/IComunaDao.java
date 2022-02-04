package cl.divap.modoaps.app.models.dao.comuna;

import cl.divap.modoaps.app.models.entity.Comuna;

import java.util.List;

public interface IComunaDao {

    public List<Comuna> findAll();

    public Comuna findByCodigoComuna(Long codigoComuna);

    public List<Comuna> findComunaByIdServicioSalud(Long id);
}
