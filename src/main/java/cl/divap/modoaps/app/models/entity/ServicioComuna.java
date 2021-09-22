package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ServicioComuna implements Serializable {

    private static final long serialVersionUID = -2265824216374852267L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ServicioSalud servicioSalud;

    @Column(name = "servicio_salud", nullable = false)
    private String servicioSaludNombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_comuna", referencedColumnName = "codigo_comuna") // Referencia al campo codigo_comuna de la clase (tabla) Comuna, por defecto sería el campo id si no colocamos el referencedColumnName
    private Comuna comuna;

    @Column(length = 100, nullable=false)
    private String comunaNombre;

    @Column(nullable = false)
    private Integer perfil_id;

    @Column(length = 50, nullable=false)
    private String perfilNombre;

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

    public String getServicioSaludNombre() {
        return servicioSaludNombre;
    }

    public void setServicioSaludNombre(String servicioSaludNombre) {
        this.servicioSaludNombre = servicioSaludNombre;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public String getComunaNombre() {
        return comunaNombre;
    }

    public void setComunaNombre(String comunaNombre) {
        this.comunaNombre = comunaNombre;
    }

    public Integer getPerfil_id() {
        return perfil_id;
    }

    public void setPerfil_id(Integer perfil_id) {
        this.perfil_id = perfil_id;
    }

    public String getPerfilNombre() {
        return perfilNombre;
    }

    public void setPerfilNombre(String perfilNombre) {
        this.perfilNombre = perfilNombre;
    }

    @Override
    public String toString() {
        return "ServicioComuna{" +
                "id=" + id +
                ", servicioSalud=" + servicioSalud +
                ", servicioSaludNombre='" + servicioSaludNombre + '\'' +
                ", comuna=" + comuna +
                ", comunaNombre='" + comunaNombre + '\'' +
                ", perfil_id=" + perfil_id +
                ", perfilNombre='" + perfilNombre + '\'' +
                '}';
    }
}
