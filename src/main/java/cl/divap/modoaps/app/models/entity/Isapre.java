package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Isapre implements Serializable {

    private static final long serialVersionUID = -7979695534696934149L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String isapre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsapre() {
        return isapre;
    }

    public void setIsapre(String isapre) {
        this.isapre = isapre;
    }

    @Override
    public String toString() {
        return "Isapre{" +
                "id=" + id +
                ", isapre='" + isapre + '\'' +
                '}';
    }
}
