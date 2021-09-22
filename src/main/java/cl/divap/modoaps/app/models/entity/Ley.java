package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Ley implements Serializable {

    private static final long serialVersionUID = 4273038894851796384L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ley;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLey() {
        return ley;
    }

    public void setLey(String ley) {
        this.ley = ley;
    }

    @Override
    public String toString() {
        return "Ley{" +
                "id=" + id +
                ", ley='" + ley + '\'' +
                '}';
    }
}
