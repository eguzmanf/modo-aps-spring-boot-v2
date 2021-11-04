package cl.divap.modoaps.app.models.service;

import cl.divap.modoaps.app.models.dao.contrato.IContratoDao;
import cl.divap.modoaps.app.models.dao.funcionario.ICustomContratoDao;
import cl.divap.modoaps.app.models.dao.funcionario.IFuncionarioDao;
import cl.divap.modoaps.app.models.dao.role.IRoleDao;
import cl.divap.modoaps.app.models.dao.usuario.ICustomUsuarioDao;
import cl.divap.modoaps.app.models.dao.usuario.IUsuarioDao;
import cl.divap.modoaps.app.models.entity.Contrato;
import cl.divap.modoaps.app.models.entity.Funcionario;
import cl.divap.modoaps.app.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class FuncionarioService implements IFuncionarioService {

    @Autowired
    private IFuncionarioDao funcionarioDao;

    @Autowired
    private IContratoDao contratoDao;

    @Autowired
    private ICustomContratoDao customContratoDao;

    @Autowired
    private IUsuarioDao usuarioDao;

    @Autowired
    private ICustomUsuarioDao customUsuarioDao;

    @Autowired
    private IRoleDao roleDao;

    @Override
    @Transactional(readOnly = true)
    public List<Funcionario> findAll() {
        //
        return (List<Funcionario>) funcionarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Funcionario> findAll(Pageable pageable) {
        return funcionarioDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Funcionario funcionario) {
        //
        funcionarioDao.save(funcionario);
    }

    @Override
    @Transactional(readOnly = true)
    public Funcionario findOne(Long id) {
        //
        return funcionarioDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        //
        funcionarioDao.deleteById(id);
    }

    @Override
    @Transactional
    public void saveContrato(Contrato contrato) {
        //
        contratoDao.save(contrato);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrato findContratoById(Long id) {
        //
        return contratoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveContratoCustom(Contrato contrato) {
        //
        customContratoDao.saveContratoCustom(contrato);
    }

    @Override
    @Transactional
    public void deleteContrato(Long id) {
        //
        contratoDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Contrato fetchByIdWithFuncionario(Long id) {
        return contratoDao.fetchByIdWithFuncionario(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Funcionario fetchByIdWithContratos(Long id) {
        return funcionarioDao.fetchByIdWithContratos(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllUsuarios() {

        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAllUsuario(Pageable pageable) {
        //
        return usuarioDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void saveUsuario(Usuario usuario) {
        //
        usuarioDao.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario ifExistUsuarioServicio(Usuario usuario) {
        //
        return customUsuarioDao.ifExistUsuarioServicio(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario ifExistUsuarioComuna(Usuario usuario) {
        //
        return customUsuarioDao.ifExistUsuarioComuna(usuario);
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario findUsuarioById(Long id) {
        //
        return usuarioDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void updateRoleByIdUsuario(Usuario usuario) {
        //
        roleDao.updateRoleByIdUsuario(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario ifExistUsuarioServicioEdit(Usuario usuario) {
        return customUsuarioDao.ifExistUsuarioServicioEdit(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario ifExistUsuarioComunaEdit(Usuario usuario) {
        return customUsuarioDao.ifExistUsuarioComunaEdit(usuario);
    }

    @Override
    @Transactional
    public void deleteUsuario(Long id) {
        //
        usuarioDao.deleteById(id);
    }

    @Transactional
    @Override
    public void updatePasswordByIdUsuario(Usuario usuario) {
        customUsuarioDao.updatePasswordByIdUsuario(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllCriteriaApi() {
        return customUsuarioDao.findAllCriteriaApi();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> findAllCriteriaApi(Pageable pageable, HttpSession session) {
        return customUsuarioDao.findAllCriteriaApi(pageable, session);
    }
}
