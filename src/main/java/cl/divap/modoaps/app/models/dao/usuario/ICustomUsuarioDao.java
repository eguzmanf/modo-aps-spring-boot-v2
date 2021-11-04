package cl.divap.modoaps.app.models.dao.usuario;

import cl.divap.modoaps.app.models.entity.Funcionario;
import cl.divap.modoaps.app.models.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ICustomUsuarioDao {

    public Usuario ifExistUsuarioServicio(Usuario usuario);

    public Usuario ifExistUsuarioComuna(Usuario usuario);

    public Usuario ifExistUsuarioServicioEdit(Usuario usuario);

    public Usuario ifExistUsuarioComunaEdit(Usuario usuario);

    public void updatePasswordByIdUsuario(Usuario usuario);

    public List<Usuario> findAllCriteriaApi();

    public Page<Usuario> findAllCriteriaApi(Pageable pageable, HttpSession session);
}
