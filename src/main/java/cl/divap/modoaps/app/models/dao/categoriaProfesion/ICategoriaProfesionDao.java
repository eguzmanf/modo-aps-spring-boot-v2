package cl.divap.modoaps.app.models.dao.categoriaProfesion;

import cl.divap.modoaps.app.models.entity.CategoriaProfesion;

import java.util.List;

public interface ICategoriaProfesionDao {

    public List<CategoriaProfesion> findAll();
}
