package cl.divap.modoaps.app.models.dao.funcionario;

import cl.divap.modoaps.app.models.entity.Contrato;
import cl.divap.modoaps.app.models.entity.Funcionario;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.*;

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
        return em.createQuery("SELECT SUM(c.jornadaLaboral) FROM Contrato c LEFT JOIN Funcionario f on f.id = c.funcionario.id WHERE c.ley.id IN (1, 3) AND c.enabled = true AND f.run = :rut")
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

    @Override
    public void updateTrueRevisadoByIdContrato(Long id, String usuario, Date fecha) {
        em.createNativeQuery("UPDATE contratos SET revisado = :rev, fecha_revision = :fecha, usuario_revisor = :usuario WHERE id = :contratoId")
                .setParameter("rev", true)
                .setParameter("fecha", fecha)
                .setParameter("usuario", usuario)
                .setParameter("contratoId", id)
                .executeUpdate();
    }

    @Override
    public void updateFalseEnabledByIdContrato(Long id, String usuario, Date fecha) {
        em.createNativeQuery("UPDATE contratos SET enabled = :enabled, fecha_disabled = :fecha WHERE id = :contratoId")
                .setParameter("enabled", false)
                .setParameter("fecha", fecha)
                .setParameter("contratoId", id)
                .executeUpdate();
    }

    @Override
    public List<Contrato> findAllContratosNotDisabled() {
        return em.createQuery("SELECT c from Contrato c WHERE c.enabled = true ORDER BY c.funcionario.run, c.comuna.id, c.establecimiento.id, c.ley.id, c.tipoContrato.id ASC").getResultList();
    }

    @Override
    public void updateTrueValidadoByIdContrato(Long id, String usuario, Date fecha) {
        em.createNativeQuery("UPDATE contratos SET validado = :validado, fecha_validacion = :fecha, usuario_validador = :usuario WHERE id = :contratoId")
                .setParameter("validado", true)
                .setParameter("fecha", fecha)
                .setParameter("usuario", usuario)
                .setParameter("contratoId", id)
                .executeUpdate();
    }

    @Override
    public List<Contrato> findContratosNotDisabledRoleServicio(Long idServicio) {
        return em.createQuery("SELECT c from Contrato c WHERE c.enabled = true AND c.servicioSalud.id = ?1 ORDER BY c.funcionario.run, c.comuna.id, c.establecimiento.id, c.ley.id, c.tipoContrato.id ASC")
                .setParameter(1, idServicio)
                .getResultList();
    }

    @Override
    public List<Contrato> findContratosNotDisabledRoleComuna(Long idServicio, Long idComuna) {
        return em.createQuery("SELECT c from Contrato c WHERE c.enabled = true AND c.servicioSalud.id = ?1 AND c.comuna.codigoComuna = ?2 ORDER BY c.funcionario.run, c.comuna.id, c.establecimiento.id, c.ley.id, c.tipoContrato.id ASC")
                .setParameter(1, idServicio)
                .setParameter(2, idComuna)
                .getResultList();
    }

    @Override
    public List<Contrato> findContratosNotDisabledRoleLaGranja() {
        return em.createQuery("SELECT c from Contrato c WHERE c.enabled = true AND c.servicioSalud.id IN (13, 14) ORDER BY c.funcionario.run, c.comuna.id, c.establecimiento.id, c.ley.id, c.tipoContrato.id ASC").getResultList();
    }

    @Override
    public List<Contrato> searchContratosCriteriaApi(HttpSession session) {
        //
        Map params = (Map) session.getAttribute("params");
        logger.info("Map params from Dao: " + params);
        logger.info("Map param userLoggedIn from Dao: " + params.get("userLoggedIn"));
        logger.info("Map param roleString from Dao: " + params.get("roleString"));
        logger.info("Map param servicioString from Dao: " + params.get("servicioString"));
        logger.info("Map param comunaString from Dao: " + params.get("comunaString"));
        logger.info("Map param run from Dao: " + params.get("run"));

        Long servicioLong = Long.parseLong(params.get("servicioString").toString());
        Long comunaLong = Long.parseLong(params.get("comunaString").toString());

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contrato> cq = cb.createQuery(Contrato.class);

        Root<Contrato> contrato = cq.from(Contrato.class);
        cq.select(contrato);

        List<Predicate> predicates = new ArrayList<>();

        List<Long> parentList = Arrays.asList(13L, 14L);

        if(params.get("run") != "" || params.get("run") != null) {
            predicates.add(cb.like(contrato.get("funcionario").get("run"),params.get("run") + "%"));
        }

        if(params.get("nombres") != "" || params.get("nombres") != null) {
            predicates.add(cb.like(contrato.get("funcionario").get("nombres"),"%" + params.get("nombres") + "%"));
        }

        if(params.get("apellidoPaterno") != "" || params.get("apellidoPaterno") != null) {
            predicates.add(cb.like(contrato.get("funcionario").get("apellidoPaterno"),"%" + params.get("apellidoPaterno") + "%"));
        }

        if(params.get("apellidoMaterno") != "" || params.get("apellidoMaterno") != null) {
            predicates.add(cb.like(contrato.get("funcionario").get("apellidoMaterno"),"%" + params.get("apellidoMaterno") + "%"));
        }

        if(params.get("sexo") != null) {
            predicates.add(cb.equal(contrato.get("funcionario").get("sexo").get("id"), params.get("sexo")));
        }

        if(params.get("nacionalidad") != null) {
            predicates.add(cb.equal(contrato.get("funcionario").get("nacionalidad").get("id"), params.get("nacionalidad")));
        }

        if(params.get("servicioSalud") != null) {
            predicates.add(cb.equal(contrato.get("servicioSalud").get("id"), params.get("servicioSalud")));
        }

        if(params.get("comuna") != null) {
            predicates.add(cb.equal(contrato.get("comuna").get("codigoComuna"), params.get("comuna")));
        }

        if(params.get("establecimiento") != null) {
            predicates.add(cb.equal(contrato.get("establecimiento").get("codigoNuevo"), params.get("establecimiento")));
        }

        if(params.get("roleString").equals("[ROLE_MINSAL]")) {
            // nothing to do!!!
        } else if(params.get("roleString").equals("[ROLE_SERVICIO]")) {
            predicates.add(cb.equal(contrato.get("servicioSalud").get("id"), servicioLong));

        } else if(params.get("roleString").equals("[ROLE_COMUNA]")) {
            predicates.add(cb.equal(contrato.get("servicioSalud").get("id"), servicioLong));
            predicates.add(cb.equal(contrato.get("comuna").get("codigoComuna"), comunaLong));

        } else if(params.get("roleString").equals("[ROLE_LA_GRANJA]")) {
            predicates.add(contrato.get("servicioSalud").get("id").in(parentList));
        }

        predicates.add(cb.equal(contrato.get("enabled"), true));

        cq.where(predicates.toArray(new Predicate[0]));

        cq.orderBy(cb.asc(contrato.get("funcionario").get("run")), cb.asc(contrato.get("comuna").get("id")), cb.asc(contrato.get("establecimiento").get("id")), cb.asc(contrato.get("ley").get("id")), cb.asc(contrato.get("tipoContrato").get("id")));

        TypedQuery<Contrato> query = em.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public Object getJornadaLaboralByIdContrato(Long id) {
        return em.createQuery("SELECT c.jornadaLaboral FROM Contrato c WHERE c.id = ?1")
                .setParameter(1, id)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void updateJornadaLaboralByIdContrato(Long id, Integer jornada) {
        em.createNativeQuery("UPDATE contratos SET jornada_laboral = :jornada WHERE id = :contratoId")
                .setParameter("jornada", jornada)
                .setParameter("contratoId", id)
                .executeUpdate();
    }
}
