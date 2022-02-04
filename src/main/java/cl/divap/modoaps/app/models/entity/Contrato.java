package cl.divap.modoaps.app.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "contratos")
public class Contrato implements Serializable {

    private static final long serialVersionUID = -1605715060560984631L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private ServicioSalud servicioSalud;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_comuna", referencedColumnName = "codigo_comuna") // Referencia al campo codigo_comuna de la clase (tabla) Comuna, por defecto sería el campo id si no colocamos el referencedColumnName
    private Comuna comuna;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_nuevo_establecimiento", referencedColumnName = "codigo_nuevo")
    private Establecimiento establecimiento;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private AdministracionSalud adminSalud;

    // @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Ley ley;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoContrato tipoContrato;

    // @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoriaProfesion categoriaProfesion;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private NivelCarrera nivelCarrera;

    // @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Profesion profesion;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Especialidad especialidad;

    // @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Cargo cargo;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private AsignacionChofer asignacionChofer;

    @NotNull
    @Max(44)
    @Column(length = 2, nullable = false)
    private Integer jornadaLaboral;

    @NotNull
    @Max(60)
    @Column(length = 2, nullable = false)
    private Integer aniosServicio;

    @PastOrPresent
    @NotNull
    @Column(name = "fecha_ingreso", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaIngreso;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Bienios bienios;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Prevision prevision;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Isapre isapre;

    @NotNull
    @Column(nullable = false)
    private Long sueldoBase;

    @NotNull
    @Column(nullable = false)
    private Long totalHaberes;

    @Column(nullable=true)
    private Boolean validado;

    @Column(nullable=true)
    private Boolean revisado;

    @Column(name = "create_at", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date createAt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)	// se creará automaticamente la columna funcionario_id de manera explicita
    private Funcionario funcionario;

    @Column(nullable=false)
    private Boolean enabled;

    @Column(name = "fecha_disabled", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDisabled;

    @Column(name = "fecha_edicion", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    @Column(name = "usuario_creador", length = 12, nullable=true)
    private String usuarioCreador;

    @Column(name = "usuario_editor", length = 12, nullable=true)
    private String usuarioEditor;

    @Column(name = "fecha_carga", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCarga;

    @Column(name = "tipo_ingreso_registro", nullable=true)
    private String tipoIngresoRegistro;

    @Column(name = "fecha_validacion", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaValidacion;

    @Column(name = "usuario_validador", length = 12, nullable=true)
    private String usuarioValidador;

    @Column(name = "fecha_revision", nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRevision;

    @Column(name = "usuario_revisor", length = 12, nullable=true)
    private String usuarioRevisor;

    @Transient
    private MultipartFile cargaMasiva;

    @Transient
    private String rutFuncionario;

    public Contrato() {}

    public Contrato(ServicioSalud servicioSalud, Comuna comuna, Establecimiento establecimiento, AdministracionSalud adminSalud, Ley ley, TipoContrato tipoContrato, CategoriaProfesion categoriaProfesion,
                    NivelCarrera nivelCarrera, Profesion profesion, Especialidad especialidad, Cargo cargo, AsignacionChofer asignacionChofer, Integer jornadaLaboral, Integer aniosServicio, Date fechaIngreso,
                    Bienios bienios, Prevision prevision, Isapre isapre, Long sueldoBase, Long totalHaberes, Boolean validado, Boolean revisado, Date createAt, Funcionario funcionario, Boolean enabled,
                    Date fechaDisabled, Date fechaEdicion, String usuarioCreador, String usuarioEditor, Date fechaCarga, String tipoIngresoRegistro, Date fechaValidacion, String usuarioValidador, Date fechaRevision,
                    String usuarioRevisor, String rutFuncionario)
    {
        this.servicioSalud = servicioSalud;
        this.comuna = comuna;
        this.establecimiento = establecimiento;
        this.adminSalud = adminSalud;
        this.ley = ley;
        this.tipoContrato = tipoContrato;
        this.categoriaProfesion = categoriaProfesion;
        this.nivelCarrera = nivelCarrera;
        this.profesion = profesion;
        this.especialidad = especialidad;
        this.cargo = cargo;
        this.asignacionChofer = asignacionChofer;
        this.jornadaLaboral = jornadaLaboral;
        this.aniosServicio = aniosServicio;
        this.fechaIngreso = fechaIngreso;
        this.bienios = bienios;
        this.prevision = prevision;
        this.isapre = isapre;
        this.sueldoBase = sueldoBase;
        this.totalHaberes = totalHaberes;
        this.validado = validado;
        this.revisado = revisado;
        this.createAt = createAt;
        this.funcionario = funcionario;
        this.enabled = enabled;
        this.fechaDisabled = fechaDisabled;
        this.fechaEdicion = fechaEdicion;
        this.usuarioCreador = usuarioCreador;
        this.usuarioEditor = usuarioEditor;
        this.fechaCarga = fechaCarga;
        this.tipoIngresoRegistro = tipoIngresoRegistro;
        this.fechaValidacion = fechaValidacion;
        this.usuarioValidador = usuarioValidador;
        this.fechaRevision = fechaRevision;
        this.usuarioRevisor = usuarioRevisor;
        this.rutFuncionario = rutFuncionario;
    }

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Valid
    public ServicioSalud getServicioSalud() {
        return servicioSalud;
    }

    public void setServicioSalud(ServicioSalud servicioSalud) {
        this.servicioSalud = servicioSalud;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public Establecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(Establecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public AdministracionSalud getAdminSalud() {
        return adminSalud;
    }

    public void setAdminSalud(AdministracionSalud adminSalud) {
        this.adminSalud = adminSalud;
    }

    @Valid
    public Ley getLey() {
        return ley;
    }

    public void setLey(Ley ley) {
        this.ley = ley;
    }

    public TipoContrato getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(TipoContrato tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    @Valid
    public CategoriaProfesion getCategoriaProfesion() {
        return categoriaProfesion;
    }

    public void setCategoriaProfesion(CategoriaProfesion categoriaProfesion) {
        this.categoriaProfesion = categoriaProfesion;
    }

    public NivelCarrera getNivelCarrera() {
        return nivelCarrera;
    }

    public void setNivelCarrera(NivelCarrera nivelCarrera) {
        this.nivelCarrera = nivelCarrera;
    }

    @Valid
    public Profesion getProfesion() {
        return profesion;
    }

    public void setProfesion(Profesion profesion) {
        this.profesion = profesion;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    @Valid
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public AsignacionChofer getAsignacionChofer() {
        return asignacionChofer;
    }

    public void setAsignacionChofer(AsignacionChofer asignacionChofer) {
        this.asignacionChofer = asignacionChofer;
    }

    public Integer getJornadaLaboral() {
        return jornadaLaboral;
    }

    public void setJornadaLaboral(Integer jornadaLaboral) {
        this.jornadaLaboral = jornadaLaboral;
    }

    public Integer getAniosServicio() {
        return aniosServicio;
    }

    public void setAniosServicio(Integer aniosServicio) {
        this.aniosServicio = aniosServicio;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Bienios getBienios() {
        return bienios;
    }

    public void setBienios(Bienios bienios) {
        this.bienios = bienios;
    }

    public Prevision getPrevision() {
        return prevision;
    }

    public void setPrevision(Prevision prevision) {
        this.prevision = prevision;
    }

    public Isapre getIsapre() {
        return isapre;
    }

    public void setIsapre(Isapre isapre) {
        this.isapre = isapre;
    }

    public Long getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(Long sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public Long getTotalHaberes() {
        return totalHaberes;
    }

    public void setTotalHaberes(Long totalHaberes) {
        this.totalHaberes = totalHaberes;
    }

    public Boolean getValidado() {
        return validado;
    }

    public void setValidado(Boolean validado) {
        this.validado = validado;
    }

    public Boolean getRevisado() {
        return revisado;
    }

    public void setRevisado(Boolean revisado) {
        this.revisado = revisado;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getFechaDisabled() {
        return fechaDisabled;
    }

    public void setFechaDisabled(Date fechaDisabled) {
        this.fechaDisabled = fechaDisabled;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public String getUsuarioEditor() {
        return usuarioEditor;
    }

    public void setUsuarioEditor(String usuarioEditor) {
        this.usuarioEditor = usuarioEditor;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getTipoIngresoRegistro() {
        return tipoIngresoRegistro;
    }

    public void setTipoIngresoRegistro(String tipoIngresoRegistro) {
        this.tipoIngresoRegistro = tipoIngresoRegistro;
    }

    public Date getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(Date fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public String getUsuarioValidador() {
        return usuarioValidador;
    }

    public void setUsuarioValidador(String usuarioValidador) {
        this.usuarioValidador = usuarioValidador;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getUsuarioRevisor() {
        return usuarioRevisor;
    }

    public void setUsuarioRevisor(String usuarioRevisor) {
        this.usuarioRevisor = usuarioRevisor;
    }

    public MultipartFile getCargaMasiva() {
        return cargaMasiva;
    }

    public void setCargaMasiva(MultipartFile cargaMasiva) {
        this.cargaMasiva = cargaMasiva;
    }

    public String getRutFuncionario() {
        return rutFuncionario;
    }

    public void setRutFuncionario(String rutFuncionario) {
        this.rutFuncionario = rutFuncionario;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", servicioSalud=" + servicioSalud +
                ", comuna=" + comuna +
                ", establecimiento=" + establecimiento +
                ", adminSalud=" + adminSalud +
                ", ley=" + ley +
                ", tipoContrato=" + tipoContrato +
                ", categoriaProfesion=" + categoriaProfesion +
                ", nivelCarrera=" + nivelCarrera +
                ", profesion=" + profesion +
                ", especialidad=" + especialidad +
                ", cargo=" + cargo +
                ", asignacionChofer=" + asignacionChofer +
                ", jornadaLaboral=" + jornadaLaboral +
                ", aniosServicio=" + aniosServicio +
                ", fechaIngreso=" + fechaIngreso +
                ", bienios=" + bienios +
                ", prevision=" + prevision +
                ", isapre=" + isapre +
                ", sueldoBase=" + sueldoBase +
                ", totalHaberes=" + totalHaberes +
                ", validado=" + validado +
                ", revisado=" + revisado +
                ", createAt=" + createAt +
                ", funcionario=" + funcionario +
                ", enabled=" + enabled +
                ", fechaDisabled=" + fechaDisabled +
                ", fechaEdicion=" + fechaEdicion +
                ", usuarioCreador='" + usuarioCreador + '\'' +
                ", usuarioEditor='" + usuarioEditor + '\'' +
                ", fechaCarga=" + fechaCarga +
                ", tipoIngresoRegistro='" + tipoIngresoRegistro + '\'' +
                ", fechaValidacion=" + fechaValidacion +
                ", usuarioValidador='" + usuarioValidador + '\'' +
                ", fechaRevision=" + fechaRevision +
                ", usuarioRevisor='" + usuarioRevisor + '\'' +
                ", cargaMasiva=" + cargaMasiva +
                ", rutFuncionario='" + rutFuncionario + '\'' +
                '}';
    }
}
