package cl.divap.modoaps.app.models.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "establecimientos")
public class Establecimiento implements Serializable {

    private static final long serialVersionUID = -9030842399557835058L;

    // @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer idMacroZona;
    private String macroZona;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    private String regionNombre;
    private String regionAlias;
    private Integer regionOrden;
    private String regionCodigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    private ServicioSalud servicio;

    private String servicioNombre;
    private Integer idOrdenServicio;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_comuna", referencedColumnName = "codigo_comuna") // Referencia al campo codigo_comuna de la clase (tabla) Comuna, por defecto sería el campo id si no colocamos el referencedColumnName
    private Comuna comuna;

    // private ComunaSimple comuna;

    private String idComunaTexto;
    private String comunaNombre;
    private String comunaMayusc;

    private Integer idDependencia;
    private String dependencia;
    private String codigoAntiguo;

    @NotNull
    @Column(name = "codigo_nuevo", length = 30, nullable=false, unique = true)
    private Integer codigoNuevo;

    @Size(min = 2, max = 100, message="El campo establecimiento debe tener entre {min} y {max} caracteres")
    @Column(length = 100, nullable=false)
    private String establecimientoNombre;

    private String idTipo;
    private String idTipoEst;

    @NotBlank
    private String tipoEstablecimiento;

    @Column(nullable=false)
    private Integer elimID;

    private String Nivel_Atencion;
    private String DIR;
    private String Acompanamiento;

    private String iaaps;
    private Integer idServicioIdComuna;

    @Transient
    private MultipartFile cargaMasivaEstablecimientos;

    public Establecimiento(){}

    public Establecimiento(Region region, ServicioSalud servicio, Comuna comuna, String idComunaTexto, String comunaNombre,
                           String comunaMayusc, Integer codigoNuevo, String establecimientoNombre, String idTipo,
                           String idTipoEst, String tipoEstablecimiento, Integer elimID, Integer idServicioIdComuna) {
        this.region = region;
        this.servicio = servicio;
        this.comuna = comuna;
        this.idComunaTexto = idComunaTexto;
        this.comunaNombre = comunaNombre;
        this.comunaMayusc = comunaMayusc;
        this.codigoNuevo = codigoNuevo;
        this.establecimientoNombre = establecimientoNombre;
        this.idTipo = idTipo;
        this.idTipoEst = idTipoEst;
        this.tipoEstablecimiento = tipoEstablecimiento;
        this.elimID = elimID;
        this.idServicioIdComuna = idServicioIdComuna;
    }

    // Tercer Constructor del controlador: CargaMasivaEstablecimientosController
    public Establecimiento(String acompanamiento, String dir, String nivelAtencion, String codigoAntiguo, Integer codigoNuevo,
                           String comunaMayusc, String comunaNombre, String dependencia, Integer elimID, String establecimientoNombre,
                           String iaaps, String idComunaTexto, Integer idDependencia, Integer idMacroZona, Integer idOrdenServicio,
                           Integer idServicioIdComuna, String idTipo, String idTipoEst, String macroZona, String regionAlias, String regionCodigo,
                           String regionNombre, Integer regionOrden, String servicioNombre, String tipoEstablecimiento, Comuna comuna, Region region,
                           ServicioSalud servicio) {

        this.Acompanamiento = acompanamiento;
        this.DIR = dir;
        this.Nivel_Atencion = nivelAtencion;
        this.codigoAntiguo = codigoAntiguo;
        this.codigoNuevo = codigoNuevo;
        this.comunaMayusc = comunaMayusc;
        this.comunaNombre = comunaNombre;
        this.dependencia = dependencia;
        this.elimID = elimID;
        this.establecimientoNombre = establecimientoNombre;
        this.iaaps = iaaps;
        this.idComunaTexto = idComunaTexto;
        this.idDependencia = idDependencia;
        this.idMacroZona = idMacroZona;
        this.idOrdenServicio = idOrdenServicio;
        this.idServicioIdComuna = idServicioIdComuna;
        this.idTipo = idTipo;
        this.idTipoEst = idTipoEst;
        this.macroZona = macroZona;
        this.regionAlias = regionAlias;
        this.regionCodigo = regionCodigo;
        this.regionNombre = regionNombre;
        this.regionOrden = regionOrden;
        this.servicioNombre = servicioNombre;
        this.tipoEstablecimiento = tipoEstablecimiento;
        this.comuna = comuna;
        this.region = region;
        this.servicio = servicio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMacroZona() {
        return idMacroZona;
    }

    public void setIdMacroZona(Integer idMacroZona) {
        this.idMacroZona = idMacroZona;
    }

    public String getMacroZona() {
        return macroZona;
    }

    public void setMacroZona(String macroZona) {
        this.macroZona = macroZona;
    }

    @Valid
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getRegionNombre() {
        return regionNombre;
    }

    public void setRegionNombre(String regionNombre) {
        this.regionNombre = regionNombre;
    }

    public String getRegionAlias() {
        return regionAlias;
    }

    public void setRegionAlias(String regionAlias) {
        this.regionAlias = regionAlias;
    }

    public Integer getRegionOrden() {
        return regionOrden;
    }

    public void setRegionOrden(Integer regionOrden) {
        this.regionOrden = regionOrden;
    }

    public String getRegionCodigo() {
        return regionCodigo;
    }

    public void setRegionCodigo(String regionCodigo) {
        this.regionCodigo = regionCodigo;
    }

    @Valid
    public ServicioSalud getServicio() {
        return servicio;
    }

    public void setServicio(ServicioSalud servicio) {
        this.servicio = servicio;
    }

    public String getServicioNombre() {
        return servicioNombre;
    }

    public void setServicioNombre(String servicioNombre) {
        this.servicioNombre = servicioNombre;
    }

    public Integer getIdOrdenServicio() {
        return idOrdenServicio;
    }

    public void setIdOrdenServicio(Integer idOrdenServicio) {
        this.idOrdenServicio = idOrdenServicio;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public String getIdComunaTexto() {
        return idComunaTexto;
    }

    public void setIdComunaTexto(String idComunaTexto) {
        this.idComunaTexto = idComunaTexto;
    }

    public String getComunaNombre() {
        return comunaNombre;
    }

    public void setComunaNombre(String comunaNombre) {
        this.comunaNombre = comunaNombre;
    }

    public String getComunaMayusc() {
        return comunaMayusc;
    }

    public void setComunaMayusc(String comunaMayusc) {
        this.comunaMayusc = comunaMayusc;
    }

    public Integer getIdDependencia() {
        return idDependencia;
    }

    public void setIdDependencia(Integer idDependencia) {
        this.idDependencia = idDependencia;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getCodigoAntiguo() {
        return codigoAntiguo;
    }

    public void setCodigoAntiguo(String codigoAntiguo) {
        this.codigoAntiguo = codigoAntiguo;
    }

    public Integer getCodigoNuevo() {
        return codigoNuevo;
    }

    public void setCodigoNuevo(Integer codigoNuevo) {
        this.codigoNuevo = codigoNuevo;
    }

    public String getEstablecimientoNombre() {
        return establecimientoNombre;
    }

    public void setEstablecimientoNombre(String establecimientoNombre) {
        this.establecimientoNombre = establecimientoNombre;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    public String getIdTipoEst() {
        return idTipoEst;
    }

    public void setIdTipoEst(String idTipoEst) {
        this.idTipoEst = idTipoEst;
    }

    public String getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(String tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public Integer getElimID() {
        return elimID;
    }

    public void setElimID(Integer elimID) {
        this.elimID = elimID;
    }

    public String getNivel_Atencion() {
        return Nivel_Atencion;
    }

    public void setNivel_Atencion(String nivel_Atencion) {
        Nivel_Atencion = nivel_Atencion;
    }

    public String getDIR() {
        return DIR;
    }

    public void setDIR(String DIR) {
        this.DIR = DIR;
    }

    public String getAcompanamiento() {
        return Acompanamiento;
    }

    public void setAcompanamiento(String acompanamiento) {
        Acompanamiento = acompanamiento;
    }

    public String getIaaps() {
        return iaaps;
    }

    public void setIaaps(String iaaps) {
        this.iaaps = iaaps;
    }

    public Integer getIdServicioIdComuna() {
        return idServicioIdComuna;
    }

    public void setIdServicioIdComuna(Integer idServicioIdComuna) {
        this.idServicioIdComuna = idServicioIdComuna;
    }

    public MultipartFile getCargaMasivaEstablecimientos() {
        return cargaMasivaEstablecimientos;
    }

    public void setCargaMasivaEstablecimientos(MultipartFile cargaMasivaEstablecimientos) {
        this.cargaMasivaEstablecimientos = cargaMasivaEstablecimientos;
    }

    @Override
    public String toString() {
        return "Establecimiento{" +
                "id=" + id +
                ", idMacroZona=" + idMacroZona +
                ", macroZona='" + macroZona + '\'' +
                ", region=" + region +
                ", regionNombre='" + regionNombre + '\'' +
                ", regionAlias='" + regionAlias + '\'' +
                ", regionOrden=" + regionOrden +
                ", regionCodigo='" + regionCodigo + '\'' +
                ", servicio=" + servicio +
                ", servicioNombre='" + servicioNombre + '\'' +
                ", idOrdenServicio=" + idOrdenServicio +
                ", comuna=" + comuna +
                ", idComunaTexto='" + idComunaTexto + '\'' +
                ", comunaNombre='" + comunaNombre + '\'' +
                ", comunaMayusc='" + comunaMayusc + '\'' +
                ", idDependencia=" + idDependencia +
                ", dependencia='" + dependencia + '\'' +
                ", codigoAntiguo='" + codigoAntiguo + '\'' +
                ", codigoNuevo=" + codigoNuevo +
                ", establecimientoNombre='" + establecimientoNombre + '\'' +
                ", idTipo='" + idTipo + '\'' +
                ", idTipoEst='" + idTipoEst + '\'' +
                ", tipoEstablecimiento='" + tipoEstablecimiento + '\'' +
                ", elimID=" + elimID +
                ", Nivel_Atencion='" + Nivel_Atencion + '\'' +
                ", DIR='" + DIR + '\'' +
                ", Acompanamiento='" + Acompanamiento + '\'' +
                ", iaaps='" + iaaps + '\'' +
                ", idServicioIdComuna=" + idServicioIdComuna +
                '}';
    }
}
