package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "nacionalidades")
public class Nacionalidad implements Serializable {

    private static final long serialVersionUID = 4575318055684500673L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nacionalidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return "Nacionalidad{" +
                "id=" + id +
                ", nacionalidad='" + nacionalidad + '\'' +
                '}';
    }
}
