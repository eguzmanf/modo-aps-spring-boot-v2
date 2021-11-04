package cl.divap.modoaps.app.models.service;

import cl.divap.modoaps.app.models.entity.Contrato;
import cl.divap.modoaps.app.models.entity.Funcionario;
import cl.divap.modoaps.app.models.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IFuncionarioService {

    public List<Funcionario> findAll();

    public Page<Funcionario> findAll(Pageable pageable);

    public void save(Funcionario funcionario);

    public Funcionario findOne(Long id);

    public void delete(Long id);

    public void saveContrato(Contrato contrato);

    public Contrato findContratoById(Long id);

    public void saveContratoCustom(Contrato contrato);

    public void deleteContrato(Long id);

    public Contrato fetchByIdWithFuncionario(Long id);

    public Funcionario fetchByIdWithContratos(Long id);

    public List<Usuario> findAllUsuarios();

    public Page<Usuario> findAllUsuario(Pageable pageable);

    public void saveUsuario(Usuario usuario);

    public Usuario ifExistUsuarioServicio(Usuario usuario);

    public Usuario ifExistUsuarioComuna(Usuario usuario);

    public Usuario findUsuarioById(Long id);

    public void updateRoleByIdUsuario(Usuario usuario);

    public Usuario ifExistUsuarioServicioEdit(Usuario usuario);

    public Usuario ifExistUsuarioComunaEdit(Usuario usuario);

    public void deleteUsuario(Long id);

    public void updatePasswordByIdUsuario(Usuario usuario);

    public List<Usuario> findAllCriteriaApi();

    public Page<Usuario> findAllCriteriaApi(Pageable pageable, HttpSession session);
}
