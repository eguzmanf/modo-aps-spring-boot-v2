package cl.divap.modoaps.app.models.dao.servicioSalud;

import cl.divap.modoaps.app.models.entity.ServicioSalud;

import java.util.List;

public interface IServicioSaludDao {

    public List<ServicioSalud> findAll();

    public List<ServicioSalud> findServiciosLaGranja();
}
