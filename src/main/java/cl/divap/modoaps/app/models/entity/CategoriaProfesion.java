package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class CategoriaProfesion implements Serializable {

    private static final long serialVersionUID = 3721589252739138098L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoriaProfesion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoriaProfesion() {
        return categoriaProfesion;
    }

    public void setCategoriaProfesion(String categoriaProfesion) {
        this.categoriaProfesion = categoriaProfesion;
    }

    @Override
    public String toString() {
        return "CategoriaProfesion{" +
                "id=" + id +
                ", categoriaProfesion='" + categoriaProfesion + '\'' +
                '}';
    }
}
