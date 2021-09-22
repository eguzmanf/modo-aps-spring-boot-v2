package cl.divap.modoaps.app.models.dao.usuario;

import cl.divap.modoaps.app.models.entity.Usuario;

public interface ICustomUsuarioDao {

    public Usuario ifExistUsuarioServicio(Usuario usuario);

    public Usuario ifExistUsuarioComuna(Usuario usuario);
}
