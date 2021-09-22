package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Profesion implements Serializable {

    private static final long serialVersionUID = -5480800233290075723L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoriaProfesion categoriaProfesion;

    @Column(nullable = false)
    private String profesion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaProfesion getCategoriaProfesion() {
        return categoriaProfesion;
    }

    public void setCategoriaProfesion(CategoriaProfesion categoriaProfesion) {
        this.categoriaProfesion = categoriaProfesion;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @Override
    public String toString() {
        return "Profesion{" +
                "id=" + id +
                ", categoriaProfesion=" + categoriaProfesion +
                ", profesion='" + profesion + '\'' +
                '}';
    }
}
