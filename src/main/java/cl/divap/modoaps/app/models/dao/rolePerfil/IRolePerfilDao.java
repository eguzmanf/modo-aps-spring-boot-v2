package cl.divap.modoaps.app.models.dao.rolePerfil;

import cl.divap.modoaps.app.models.entity.RolePerfil;

import java.util.List;

public interface IRolePerfilDao {

    public List<RolePerfil> findAll();

    public RolePerfil findById(Long id);
}
