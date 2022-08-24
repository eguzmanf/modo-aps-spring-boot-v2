package cl.divap.modoaps.app.models.service;

import cl.divap.modoaps.app.models.dao.contrato.IContratoDao;
import cl.divap.modoaps.app.models.dao.descuentoRetiro.ICustomDescuentoRetiroDao;
import cl.divap.modoaps.app.models.dao.funcionario.ICustomContratoDao;
import cl.divap.modoaps.app.models.dao.funcionario.ICustomFuncionarioDao;
import cl.divap.modoaps.app.models.dao.funcionario.IFuncionarioDao;
import cl.divap.modoaps.app.models.dao.role.IRoleDao;
import cl.divap.modoaps.app.models.dao.servicioSaludComuna.ICustomServicioComunaDao;
import cl.divap.modoaps.app.models.dao.servicioSaludComuna.IServicioComunaDao;
import cl.divap.modoaps.app.models.dao.usuario.ICustomUsuarioDao;
import cl.divap.modoaps.app.models.dao.usuario.IUsuarioDao;
import cl.divap.modoaps.app.models.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
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

    @Autowired
    private ICustomServicioComunaDao customServicioComunaDao;

    @Autowired
    private ICustomFuncionarioDao customFuncionarioDao;

    @Autowired
    private ICustomDescuentoRetiroDao customDescuentoRetiroDao;

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

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> findAllContratos() {
        //
        return customContratoDao.findAllContratos();
    }

    @Override
    @Transactional(readOnly = true)
    public List getIdServicioByServicioName(String servicioName) {
        return customContratoDao.getIdServicioByServicioName(servicioName);
    }

    @Override
    @Transactional(readOnly = true)
    public List getIdComunaByComunaName(String comunaName) {
        return customContratoDao.getIdComunaByComunaName(comunaName);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdEstablecimientoByEstablecimientoName(String establecimientoName) {
        return customContratoDao.getIdEstablecimientoByEstablecimientoName(establecimientoName);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdAdminSaludByAdminSaludName(String adminSalud) {
        return customContratoDao.getIdAdminSaludByAdminSaludName(adminSalud);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdSexoBySexoName(String sexo) {
        return customContratoDao.getIdSexoBySexoName(sexo);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdNacionalidadByNacionalidadName(String nacionalidad) {
        return customContratoDao.getIdNacionalidadByNacionalidadName(nacionalidad);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean ifExistFuncionarioRut(String rutCompleto) {
        return customContratoDao.ifExistFuncionarioRut(rutCompleto);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdLeyByLeyName(String ley) {
        return customContratoDao.getIdLeyByLeyName(ley);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdTipoContratoByTipoContratoNameAndIdLeyLong(String tipoContrato, Long idLeyLong) {
        return customContratoDao.getIdTipoContratoByTipoContratoNameAndIdLeyLong(tipoContrato, idLeyLong);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdCategoriaProfesionByCategoriaProfesionName(String categoriaProfesion) {
        return customContratoDao.getIdCategoriaProfesionByCategoriaProfesionName(categoriaProfesion);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdCategoriaProfesionByCategoriaProfesionNameLeyHonorarioAndCodigoTrabajo(String categoriaProfesion) {
        return customContratoDao.getIdCategoriaProfesionByCategoriaProfesionNameLeyHonorarioAndCodigoTrabajo(categoriaProfesion);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdNivelCarreraByNivelCarreraNameLey19378(String nivelCarrera) {
        return customContratoDao.getIdNivelCarreraByNivelCarreraNameLey19378(nivelCarrera);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdNivelCarreraByNivelCarreraNameLeyHonorarioAndCodigoTrabajo(String nivelCarrera) {
        return customContratoDao.getIdNivelCarreraByNivelCarreraNameLeyHonorarioAndCodigoTrabajo(nivelCarrera);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdProfesionByProfesionNameAndIdCategoriaProfesionLong(String profesion, Long idCategoriaProfesionLong) {
        return customContratoDao.getIdProfesionByProfesionNameAndIdCategoriaProfesionLong(profesion, idCategoriaProfesionLong);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdProfesionByProfesionNameAndIdCategoriaProfesionLongLeyHonorarioAndCodigoTrabajo(String profesion, Long idCategoriaProfesionLong) {
        return customContratoDao.getIdProfesionByProfesionNameAndIdCategoriaProfesionLongLeyHonorarioAndCodigoTrabajo(profesion, idCategoriaProfesionLong);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdEspecialidadByEspecialidadNameAndIdProfesionLong(String especialidad, Long idProfesionLong) {
        return customContratoDao.getIdEspecialidadByEspecialidadNameAndIdProfesionLong(especialidad, idProfesionLong);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdCargoByCargoName(String cargo) {
        return customContratoDao.getIdCargoByCargoName(cargo);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdAsignacionChoferByAsignacionChoferNameAndIdCargoLong(String asignacionChofer, Long idCargoLong) {
        return customContratoDao.getIdAsignacionChoferByAsignacionChoferNameAndIdCargoLong(asignacionChofer, idCargoLong);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getCountJornadaLaboralByRutFuncionario(String rut) {
        return customContratoDao.getCountJornadaLaboralByRutFuncionario(rut);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdBieniosByBieniosName(String bienios) {
        return customContratoDao.getIdBieniosByBieniosName(bienios);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdPrevisionByPrevisionName(String prevision) {
        return customContratoDao.getIdPrevisionByPrevisionName(prevision);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdIsapreByIsapreName(String isapre) {
        return customContratoDao.getIdIsapreByIsapreName(isapre);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getIdFuncionarioByRutFuncionario(String rut) {
        return customContratoDao.getIdFuncionarioByRutFuncionario(rut);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> getContratosByRutFuncionarioWithFuncionario(String rut) {
        return customContratoDao.getContratosByRutFuncionarioWithFuncionario(rut);
    }

    @Override
    @Transactional(readOnly = true)
    public Funcionario fetchByIdByIdServicioWithContratosRoleServicio(Long id, Long idServicio) {
        return funcionarioDao.fetchByIdByIdServicioWithContratosRoleServicio(id, idServicio);
    }

    @Override
    @Transactional(readOnly = true)
    public Funcionario fetchByIdByIdComunaByIdServicioWithContratosRoleComuna(Long id, Long idServicio, Long idComuna) {
        return funcionarioDao.fetchByIdByIdComunaByIdServicioWithContratosRoleComuna(id, idServicio, idComuna);
    }

    @Override
    @Transactional(readOnly = true)
    public Funcionario fetchByIdWithContratosRoleLaGranja(Long id) {
        return funcionarioDao.fetchByIdWithContratosRoleLaGranja(id);
    }

    @Override
    @Transactional
    public void updateTrueRevisadoByIdContrato(Long id, String usuario, Date fecha) {
        customContratoDao.updateTrueRevisadoByIdContrato(id, usuario, fecha);
    }

    @Override
    @Transactional
    public void updateFalseEnabledByIdContrato(Long id, String usuario, Date fecha) {
        customContratoDao.updateFalseEnabledByIdContrato(id, usuario, fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> findAllContratosNotDisabled() {
        return customContratoDao.findAllContratosNotDisabled();
    }

    @Override
    @Transactional
    public void updateTrueValidadoByIdContrato(Long id, String usuario, Date fecha) {
        customContratoDao.updateTrueValidadoByIdContrato(id, usuario, fecha);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> findContratosNotDisabledRoleServicio(Long idServicio) {
        return customContratoDao.findContratosNotDisabledRoleServicio(idServicio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> findContratosNotDisabledRoleComuna(Long idServicio, Long idComuna) {
        return customContratoDao.findContratosNotDisabledRoleComuna(idServicio, idComuna);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> findContratosNotDisabledRoleLaGranja() {
        return customContratoDao.findContratosNotDisabledRoleLaGranja();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrato> searchContratosCriteriaApi(HttpSession session) {
        return customContratoDao.searchContratosCriteriaApi(session);
    }

    @Override
    @Transactional(readOnly = true)
    public Object getJornadaLaboralByIdContrato(Long id) {
        return customContratoDao.getJornadaLaboralByIdContrato(id);
    }

    @Override
    @Transactional
    public void updateJornadaLaboralByIdContrato(Long id, Integer jornada) {
        customContratoDao.updateJornadaLaboralByIdContrato(id, jornada);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Funcionario> findAllCriteriaApiFuncionarios(Pageable pageable, HttpSession session) {
        return customFuncionarioDao.findAllCriteriaApiFuncionarios(pageable, session);
    }

    @Override
    @Transactional
    public void updateContratosEnabledFalse(Long id) {
        customFuncionarioDao.updateContratosEnabledFalse(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DescuentoRetiro> findAllDescuentoRetiro() {
        return customDescuentoRetiroDao.findAllDescuentoRetiro();
    }

}
