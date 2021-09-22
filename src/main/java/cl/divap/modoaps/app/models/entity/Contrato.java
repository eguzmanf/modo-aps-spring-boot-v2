package cl.divap.modoaps.app.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private ServicioSalud servicioSalud;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_comuna", referencedColumnName = "codigo_comuna") // Referencia al campo codigo_comuna de la clase (tabla) Comuna, por defecto sería el campo id si no colocamos el referencedColumnName
    private Comuna comuna;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Establecimiento establecimiento;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private AdministracionSalud adminSalud;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Ley ley;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private TipoContrato tipoContrato;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private CategoriaProfesion categoriaProfesion;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private NivelCarrera nivelCarrera;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Profesion profesion;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Especialidad especialidad;

    @Valid
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

    @Column(columnDefinition = "boolean default false", nullable=false)
    private Boolean validado;

    @Column(columnDefinition = "boolean default false", nullable=false)
    private Boolean revisado;

    @Column(name = "create_at", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date createAt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)	// se creará automaticamente la columna funcionario_id de manera explicita
    private Funcionario funcionario;

    @Column(columnDefinition = "boolean default true", nullable=false)
    private Boolean enabled;

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
                '}';
    }
}
