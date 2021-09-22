package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "comunas")
public class Comuna implements Serializable {

    private static final long serialVersionUID = 6997150059664716963L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    private ServicioSalud servicioSalud;

    @NotNull
    @Column(name = "codigo_comuna", nullable = false)
    private Long codigoComuna;

    @Column(nullable = false)
    private String comuna;

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

    public ServicioSalud getServicioSalud() {
        return servicioSalud;
    }

    public void setServicioSalud(ServicioSalud servicioSalud) {
        this.servicioSalud = servicioSalud;
    }

    public Long getCodigoComuna() {
        return codigoComuna;
    }

    public void setCodigoComuna(Long codigoComuna) {
        this.codigoComuna = codigoComuna;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    @Override
    public String toString() {
        return "Comuna{" +
                "id=" + id +
                ", region=" + region +
                ", servicioSalud=" + servicioSalud +
                ", codigoComuna=" + codigoComuna +
                ", comuna='" + comuna + '\'' +
                '}';
    }
}
