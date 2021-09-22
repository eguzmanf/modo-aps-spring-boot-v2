package cl.divap.modoaps.app.models.dao.cargo;

import cl.divap.modoaps.app.models.entity.Cargo;

import java.util.List;

public interface ICargoDao {

    public List<Cargo> findAll();
}
