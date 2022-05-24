package cl.divap.modoaps.app.models.dao.funcionario;

import cl.divap.modoaps.app.models.entity.Contrato;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public interface ICustomContratoDao {

    public void saveContratoCustom(Contrato contrato);

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

    public void updateTrueRevisadoByIdContrato(Long id, String usuario, Date fecha);

    public void updateFalseEnabledByIdContrato(Long id, String usuario, Date fecha);

    public List<Contrato> findAllContratosNotDisabled();

    public void updateTrueValidadoByIdContrato(Long id, String usuario, Date fecha);

    public List<Contrato> findContratosNotDisabledRoleServicio(Long idServicio);

    public List<Contrato> findContratosNotDisabledRoleComuna(Long idServicio, Long idComuna);

    public List<Contrato> findContratosNotDisabledRoleLaGranja();

    public List<Contrato> searchContratosCriteriaApi(HttpSession session);

    public Object getJornadaLaboralByIdContrato(Long id);

    public void updateJornadaLaboralByIdContrato(Long id, Integer jornada);
}
