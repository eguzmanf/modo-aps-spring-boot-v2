package cl.divap.modoaps.app.models.dao.funcionario;

import cl.divap.modoaps.app.models.entity.Contrato;
import cl.divap.modoaps.app.models.entity.Funcionario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomContratoDao implements ICustomContratoDao {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @PersistenceContext
    private EntityManager em;

    @Override
    public void saveContratoCustom(Contrato contrato) {
        //
        if(contrato.getId() != null && contrato.getId() > 0) {
            logger.info("Ingresando en bucle editar (merge) en función saveContratoCustom");
            em.merge(contrato);
        } else {
            logger.info("Ingresando en bucle crear (persist) en función saveContratoCustom");
            em.persist(contrato);
        }
    }

    @Override
    public List<Contrato> findAllContratos() {
        return em.createQuery("SELECT c from Contrato c ORDER BY c.funcionario.run, c.comuna.id, c.establecimiento.id, c.ley.id, c.tipoContrato.id ASC").getResultList();
    }

    @Override
    public List getIdServicioByServicioName(String servicioName) {
        //
        return em.createNativeQuery("SELECT id FROM servicios_salud WHERE TRIM(UPPER(servicio_salud)) = :servicioName")
                .setParameter("servicioName", servicioName.trim().toUpperCase())
                .getResultList();
    }

    @Override
    public List getIdComunaByComunaName(String comunaName) {
        //
        return em.createQuery("SELECT c.codigoComuna FROM Comuna c WHERE TRIM(UPPER(c.comuna)) = ?1")
                .setParameter(1, comunaName.trim().toUpperCase())
                .getResultList();
    }

    @Override
    public Object getIdEstablecimientoByEstablecimientoName(String establecimientoName) {
        return em.createQuery("SELECT e.codigoNuevo FROM Establecimiento e WHERE TRIM(UPPER(e.establecimientoNombre)) = ?1")
                .setParameter(1, establecimientoName.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdAdminSaludByAdminSaludName(String adminSalud) {
        return em.createQuery("SELECT a.id FROM AdministracionSalud a WHERE TRIM(UPPER(a.adminSalud)) = ?1")
                .setParameter(1, adminSalud.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdSexoBySexoName(String sexo) {
        return em.createQuery("SELECT s.id FROM Sexo s WHERE TRIM(UPPER(s.sexo)) = ?1")
                .setParameter(1, sexo.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdNacionalidadByNacionalidadName(String nacionalidad) {
        return em.createQuery("SELECT n.id FROM Nacionalidad n WHERE TRIM(UPPER(n.nacionalidad)) = ?1")
                .setParameter(1, nacionalidad.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public boolean ifExistFuncionarioRut(String rutCompleto) {

        Funcionario query = em.createQuery("from Funcionario f where trim(upper(f.run)) = ?1", Funcionario.class)
                .setParameter(1, rutCompleto.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);

        if(query != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object getIdLeyByLeyName(String ley) {
        return em.createQuery("SELECT l.id FROM Ley l WHERE TRIM(UPPER(l.ley)) = ?1")
                .setParameter(1, ley.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdTipoContratoByTipoContratoNameAndIdLeyLong(String tipoContrato, Long idLeyLong) {
        return em.createQuery("SELECT tc.id FROM TipoContrato tc WHERE TRIM(UPPER(tc.tipoContrato)) = ?1 AND tc.ley.id = ?2")
                .setParameter(1, tipoContrato.trim().toUpperCase())
                .setParameter(2, idLeyLong)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdCategoriaProfesionByCategoriaProfesionName(String categoriaProfesion) {
        return em.createQuery("SELECT cp.id FROM CategoriaProfesion cp WHERE cp.id IN (1,2,3,4,5,6) AND TRIM(UPPER(cp.categoriaProfesion)) = ?1")
                .setParameter(1, categoriaProfesion.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdCategoriaProfesionByCategoriaProfesionNameLeyHonorarioAndCodigoTrabajo(String categoriaProfesion) {
        return em.createQuery("SELECT cp.id FROM CategoriaProfesion cp WHERE cp.id = 7 AND TRIM(UPPER(cp.categoriaProfesion)) = ?1")
                .setParameter(1, categoriaProfesion.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdNivelCarreraByNivelCarreraNameLey19378(String nivelCarrera) {
        return em.createQuery("SELECT nc.id FROM NivelCarrera nc WHERE nc.id <> 16 AND TRIM(UPPER(nc.nivelCarrera)) = ?1")
                .setParameter(1, nivelCarrera.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdNivelCarreraByNivelCarreraNameLeyHonorarioAndCodigoTrabajo(String nivelCarrera) {
        return em.createQuery("SELECT nc.id FROM NivelCarrera nc WHERE nc.id = 16 AND TRIM(UPPER(nc.nivelCarrera)) = ?1")
                .setParameter(1, nivelCarrera.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdProfesionByProfesionNameAndIdCategoriaProfesionLong(String profesion, Long idCategoriaProfesionLong) {
        return em.createQuery("SELECT p.id FROM Profesion p WHERE p.categoriaProfesion.id = ?1 AND p.categoriaProfesion.id IN (1,2,3,4,5,6) AND TRIM(UPPER(p.profesion)) = ?2")
                .setParameter(1, idCategoriaProfesionLong)
                .setParameter(2, profesion.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdProfesionByProfesionNameAndIdCategoriaProfesionLongLeyHonorarioAndCodigoTrabajo(String profesion, Long idCategoriaProfesionLong) {
        return em.createQuery("SELECT p.id FROM Profesion p WHERE p.categoriaProfesion.id = ?1 AND p.categoriaProfesion.id = 7 AND TRIM(UPPER(p.profesion)) = ?2")
                .setParameter(1, idCategoriaProfesionLong)
                .setParameter(2, profesion.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdEspecialidadByEspecialidadNameAndIdProfesionLong(String especialidad, Long idProfesionLong) {
        return em.createQuery("SELECT e.id FROM Especialidad e WHERE e.profesion.id = ?1 AND TRIM(UPPER(e.especialidad)) = ?2")
                .setParameter(1, idProfesionLong)
                .setParameter(2, especialidad.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);

    }

    @Override
    public Object getIdCargoByCargoName(String cargo) {
        return em.createQuery("SELECT c.id FROM Cargo c WHERE TRIM(UPPER(c.cargo)) = ?1")
                .setParameter(1, cargo.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdAsignacionChoferByAsignacionChoferNameAndIdCargoLong(String asignacionChofer, Long idCargoLong) {
        return em.createQuery("SELECT ac.id FROM AsignacionChofer ac WHERE ac.cargo.id = ?1 AND TRIM(UPPER(ac.asignacionChofer)) = ?2")
                .setParameter(1, idCargoLong)
                .setParameter(2, asignacionChofer.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getCountJornadaLaboralByRutFuncionario(String rut) {
        return em.createQuery("SELECT SUM(c.jornadaLaboral) FROM Contrato c LEFT JOIN Funcionario f on f.id = c.funcionario.id WHERE c.ley.id IN (1, 3) AND f.run = :rut")
                .setParameter("rut", rut)
                .getSingleResult();
    }

    @Override
    public Object getIdBieniosByBieniosName(String bienios) {
        return em.createQuery("SELECT b.id FROM Bienios b WHERE TRIM(UPPER(b.bienios)) = ?1")
                .setParameter(1, bienios.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdPrevisionByPrevisionName(String prevision) {
        return em.createQuery("SELECT p.id FROM Prevision p WHERE TRIM(UPPER(p.prevision)) = ?1")
                .setParameter(1, prevision.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdIsapreByIsapreName(String isapre) {
        return em.createQuery("SELECT i.id FROM Isapre i WHERE TRIM(UPPER(i.isapre)) = ?1")
                .setParameter(1, isapre.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Object getIdFuncionarioByRutFuncionario(String rut) {
        return em.createQuery("SELECT f.id FROM Funcionario f WHERE TRIM(UPPER(f.run)) = ?1")
                .setParameter(1, rut.trim().toUpperCase())
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Contrato> getContratosByRutFuncionarioWithFuncionario(String rut) {
        // return em.createQuery("SELECT c FROM Contrato c LEFT JOIN Funcionario f ON c.funcionario.id = f.id WHERE TRIM(UPPER(f.run)) = ?1", Contrato.class)
        return em.createQuery("SELECT c FROM Contrato c WHERE TRIM(UPPER(c.funcionario.run)) = ?1", Contrato.class)
                .setParameter(1, rut.trim().toUpperCase())
                .getResultList();
    }
}
