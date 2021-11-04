package cl.divap.modoaps.app.models.dao.usuario;

import cl.divap.modoaps.app.models.entity.Usuario;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long> {

    public Usuario findByUsername(String username);
    public Usuario findByEmail(String email);
}
