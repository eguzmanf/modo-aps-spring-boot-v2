package cl.divap.modoaps.app.models.dao.tipoContrato;

import cl.divap.modoaps.app.models.entity.TipoContrato;

import java.util.List;

public interface ITipoContratoDao {

    public List<TipoContrato> findAll();

    public List<TipoContrato> findTipoContratoByLeyId(Long LeyId);

}
