package cl.divap.modoaps.app.models.dao.asignacionChofer;

import cl.divap.modoaps.app.models.entity.AsignacionChofer;

import java.util.List;

public interface IAsignacionChoferDao {

    public List<AsignacionChofer> findAll();
}
