package cl.divap.modoaps.app.models.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "descuento_retiro")
public class DescuentoRetiro implements Serializable {

    private static final long serialVersionUID = 4274477415767793191L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ServicioSalud servicioSalud;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_comuna", referencedColumnName = "codigo_comuna") // Referencia al campo codigo_comuna de la clase (tabla) Comuna, por defecto sería el campo id si no colocamos el referencedColumnName
    private Comuna comuna;

    @NotNull
    @Column(name = "fecha_convenio", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaConvenio;

    @NotNull
    @Max(72)
    @Min(0)
    @Column(length = 2, nullable = false)
    private Integer numeroCuotasTotal;

    @NotNull
    @Column(nullable = false)
    private Long montoCuotaMensual;

    @NotNull
    @Column(nullable = false)
    private Long ultimaCuota;

    @NotNull
    @Column(nullable = false)
    private Long totalRecursos;

    @NotNull
    @Column(nullable = false)
    private Long deuda;

    @NotNull
    @Max(72)
    @Min(0)
    @Column(length = 2, nullable = false)
    private Integer numeroCuota;

    @NotNull
    @Column(name = "fecha_cuota", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCuota;

    @NotBlank
    @Size(min = 2, max = 250, message="El campo Resolución debe tener entre {min} and {max} caracteres")
    @Column(name = "resolucion", length = 250, nullable=false)
    private String resolucion;

    @Column(name = "create_at", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date createAt;

    @Transient
    private MultipartFile cargaMasiva;

    public DescuentoRetiro() {}

    public DescuentoRetiro(ServicioSalud servicioSalud, Comuna comuna,Date fechaConvenio, Integer numeroCuotasTotal, Long montoCuotaMensual,
                           Long ultimaCuota, Long totalRecursos, Long deuda, Integer numeroCuota, Date fechaCuota, String resolucion, Date createAt) {
        this.servicioSalud = servicioSalud;
        this.comuna = comuna;
        this.fechaConvenio = fechaConvenio;
        this.numeroCuotasTotal = numeroCuotasTotal;
        this.montoCuotaMensual = montoCuotaMensual;
        this.ultimaCuota = ultimaCuota;
        this.totalRecursos = totalRecursos;
        this.deuda = deuda;
        this.numeroCuota = numeroCuota;
        this.fechaCuota = fechaCuota;
        this.resolucion = resolucion;
        this.createAt = createAt;
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

    public Date getFechaConvenio() {
        return fechaConvenio;
    }

    public void setFechaConvenio(Date fechaConvenio) {
        this.fechaConvenio = fechaConvenio;
    }

    public Integer getNumeroCuotasTotal() {
        return numeroCuotasTotal;
    }

    public void setNumeroCuotasTotal(Integer numeroCuotasTotal) {
        this.numeroCuotasTotal = numeroCuotasTotal;
    }

    public Long getMontoCuotaMensual() {
        return montoCuotaMensual;
    }

    public void setMontoCuotaMensual(Long montoCuotaMensual) {
        this.montoCuotaMensual = montoCuotaMensual;
    }

    public Long getUltimaCuota() {
        return ultimaCuota;
    }

    public void setUltimaCuota(Long ultimaCuota) {
        this.ultimaCuota = ultimaCuota;
    }

    public Long getTotalRecursos() {
        return totalRecursos;
    }

    public void setTotalRecursos(Long totalRecursos) {
        this.totalRecursos = totalRecursos;
    }

    public Long getDeuda() {
        return deuda;
    }

    public void setDeuda(Long deuda) {
        this.deuda = deuda;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public Date getFechaCuota() {
        return fechaCuota;
    }

    public void setFechaCuota(Date fechaCuota) {
        this.fechaCuota = fechaCuota;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public MultipartFile getCargaMasiva() {
        return cargaMasiva;
    }

    public void setCargaMasiva(MultipartFile cargaMasiva) {
        this.cargaMasiva = cargaMasiva;
    }
}
