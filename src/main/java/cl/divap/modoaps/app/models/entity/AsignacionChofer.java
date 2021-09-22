package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class AsignacionChofer implements Serializable {

    private static final long serialVersionUID = 1518471552730498545L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cargo cargo;

    @Column(nullable = false)
    private String asignacionChofer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getAsignacionChofer() {
        return asignacionChofer;
    }

    public void setAsignacionChofer(String asignacionChofer) {
        this.asignacionChofer = asignacionChofer;
    }

    @Override
    public String toString() {
        return "AsignacionChofer{" +
                "id=" + id +
                ", cargo=" + cargo +
                ", asignacionChofer='" + asignacionChofer + '\'' +
                '}';
    }
}
