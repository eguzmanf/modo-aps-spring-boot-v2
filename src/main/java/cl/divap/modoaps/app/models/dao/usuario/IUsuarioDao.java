package cl.divap.modoaps.app.models.dao.usuario;

import cl.divap.modoaps.app.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    public Usuario findByUsername(String username);
    public Usuario findByEmail(String email);
}
