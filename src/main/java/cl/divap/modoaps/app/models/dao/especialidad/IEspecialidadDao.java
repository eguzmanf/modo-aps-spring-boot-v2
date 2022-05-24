package cl.divap.modoaps.app.models.dao.especialidad;

import cl.divap.modoaps.app.models.entity.Especialidad;

import java.util.List;

public interface IEspecialidadDao {

    public List<Especialidad> findAll();

    public List<Especialidad> findEspecialidadByProfesionId(Long profesionId);
}
