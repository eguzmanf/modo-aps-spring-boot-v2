package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class AdministracionSalud implements Serializable {

    private static final long serialVersionUID = -2357884556658849695L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String adminSalud;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminSalud() {
        return adminSalud;
    }

    public void setAdminSalud(String adminSalud) {
        this.adminSalud = adminSalud;
    }

    @Override
    public String toString() {
        return "AdministracionSalud{" +
                "id=" + id +
                ", adminSalud='" + adminSalud + '\'' +
                '}';
    }
}
