package cl.divap.modoaps.app.models.service;

import cl.divap.modoaps.app.models.entity.Contrato;
import cl.divap.modoaps.app.models.entity.Funcionario;
import cl.divap.modoaps.app.models.entity.ServicioComuna;
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

    public List<Contrato> findAllContratos();

    public List getIdServicioByServicioName(String servicioName);

    public List getIdComunaByComunaName(String comunaName);

    public Object getIdEstablecimientoByEstablecimientoName(String establecimientoName);

    public Object getIdAdminSaludByAdminSaludName(String adminSalud);

    public Object getIdSexoBySexoName(String sexo);

    public Object getIdNacionalidadByNacionalidadName(String nacionalidad);

    public boolean ifExistFuncionarioRut(String rutCompleto);

    public Object getIdLeyByLeyName(String ley);

    public Object getIdTipoContratoByTipoContratoNameAndIdLeyLong(String tipoContrato, Long idLeyLong);

    public Object getIdCategoriaProfesionByCategoriaProfesionName(String categoriaProfesion);

    public Object getIdCategoriaProfesionByCategoriaProfesionNameLeyHonorarioAndCodigoTrabajo(String categoriaProfesion);

    public Object getIdNivelCarreraByNivelCarreraNameLey19378(String nivelCarrera);

    public Object getIdNivelCarreraByNivelCarreraNameLeyHonorarioAndCodigoTrabajo(String nivelCarrera);

    public Object getIdProfesionByProfesionNameAndIdCategoriaProfesionLong(String profesion, Long idCategoriaProfesionLong);

    public Object getIdProfesionByProfesionNameAndIdCategoriaProfesionLongLeyHonorarioAndCodigoTrabajo(String profesion, Long idCategoriaProfesionLong);

    public Object getIdEspecialidadByEspecialidadNameAndIdProfesionLong(String especialidad, Long idProfesionLong);

    public Object getIdCargoByCargoName(String cargo);

    public Object getIdAsignacionChoferByAsignacionChoferNameAndIdCargoLong(String asignacionChofer, Long idCargoLong);

    public Object getCountJornadaLaboralByRutFuncionario(String rut);

    public Object getIdBieniosByBieniosName(String bienios);

    public Object getIdPrevisionByPrevisionName(String prevision);

    public Object getIdIsapreByIsapreName(String isapre);

    public Object getIdFuncionarioByRutFuncionario(String rut);

    public List<Contrato> getContratosByRutFuncionarioWithFuncionario(String rut);

}
