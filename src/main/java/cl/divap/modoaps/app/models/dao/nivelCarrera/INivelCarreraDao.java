package cl.divap.modoaps.app.models.dao.nivelCarrera;

import cl.divap.modoaps.app.models.entity.CategoriaProfesion;
import cl.divap.modoaps.app.models.entity.NivelCarrera;

import java.util.List;

public interface INivelCarreraDao {

    public List<NivelCarrera> findAll();

    public List<NivelCarrera> findNivelCarreraLey19378();

    public List<NivelCarrera> findNivelCarreraLeyHonorariosCodigo();

}
