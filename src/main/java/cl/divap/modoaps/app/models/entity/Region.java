package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "regiones")
public class Region implements Serializable {

    private static final long serialVersionUID = -1582286069236703214L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String region;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", region='" + region + '\'' +
                '}';
    }
}
