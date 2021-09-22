package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "servicios_salud")
public class ServicioSalud implements Serializable {

    private static final long serialVersionUID = 2351159564776858624L;

    // @NotNull
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Region region;

    @Column(name = "servicio_salud", nullable = false)
    private String servicioSalud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public String getServicioSalud() {
        return servicioSalud;
    }

    public void setServicioSalud(String servicioSalud) {
        this.servicioSalud = servicioSalud;
    }

    @Override
    public String toString() {
        return "ServicioSalud{" +
                "id=" + id +
                ", region=" + region +
                ", servicioSalud='" + servicioSalud + '\'' +
                '}';
    }
}
