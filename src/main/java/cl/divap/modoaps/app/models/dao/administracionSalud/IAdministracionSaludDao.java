package cl.divap.modoaps.app.models.dao.administracionSalud;

import cl.divap.modoaps.app.models.entity.AdministracionSalud;

import java.util.List;

public interface IAdministracionSaludDao {

    public List<AdministracionSalud> findAll();
}
