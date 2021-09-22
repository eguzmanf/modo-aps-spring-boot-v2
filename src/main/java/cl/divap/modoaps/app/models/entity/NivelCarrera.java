package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class NivelCarrera implements Serializable {

    private static final long serialVersionUID = -4324397109734237341L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nivelCarrera;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNivelCarrera() {
        return nivelCarrera;
    }

    public void setNivelCarrera(String nivelCarrera) {
        this.nivelCarrera = nivelCarrera;
    }

    @Override
    public String toString() {
        return "NivelCarrera{" +
                "id=" + id +
                ", nivelCarrera='" + nivelCarrera + '\'' +
                '}';
    }
}
