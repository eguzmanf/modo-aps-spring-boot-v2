package cl.divap.modoaps.app.models.dao.profesion;

import cl.divap.modoaps.app.models.entity.Profesion;

import java.util.List;

public interface IProfesionDao {

    public List<Profesion> findAll();

    public List<Profesion> findProfesionLey19378(Long idCategoriaProfesion);

    public List<Profesion> findProfesionLeyHonorariosCodigo();
}
