package cl.divap.modoaps.app.models.dao.servicioSaludComuna;

import cl.divap.modoaps.app.models.entity.ServicioComuna;

import java.util.List;

public interface ICustomServicioComunaDao {

    public ServicioComuna findServicioComunaByServicioId(Long idServicio);
}
