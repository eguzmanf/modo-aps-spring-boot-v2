package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Bienios implements Serializable {

    private static final long serialVersionUID = 2586471053095498485L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bienios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBienios() {
        return bienios;
    }

    public void setBienios(String bienios) {
        this.bienios = bienios;
    }

    @Override
    public String toString() {
        return "Bienios{" +
                "id=" + id +
                ", bienios='" + bienios + '\'' +
                '}';
    }
}
