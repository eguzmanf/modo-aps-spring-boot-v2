package cl.divap.modoaps.app.models.dao.role;

import cl.divap.modoaps.app.models.entity.Role;
import cl.divap.modoaps.app.models.entity.Usuario;

public interface IRoleDao {

    public void updateRoleByIdUsuario(Usuario usuario);
}
