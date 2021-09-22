package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="roles_perfiles")
public class RolePerfil  implements Serializable {
    //
    private static final long serialVersionUID = -2221605287894062080L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rolePerfil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRolePerfil() {
        return rolePerfil;
    }

    public void setRolePerfil(String rolePerfil) {
        this.rolePerfil = rolePerfil;
    }

    @Override
    public String toString() {
        return "RolePerfil{" +
                "id=" + id +
                ", rolePerfil='" + rolePerfil + '\'' +
                '}';
    }
}
