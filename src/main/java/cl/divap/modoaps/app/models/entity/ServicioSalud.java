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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    @Column(name = "servicio_salud", nullable = false)
    private String servicioSalud;

    @NotNull
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

}
