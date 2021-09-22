package cl.divap.modoaps.app.models.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class TipoContrato implements Serializable {

    private static final long serialVersionUID = 5072023043789374393L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ley ley;

    @Column(nullable = false)
    private String tipoContrato;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ley getLey() {
        return ley;
    }

    public void setLey(Ley ley) {
        this.ley = ley;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    @Override
    public String toString() {
        return "TipoContrato{" +
                "id=" + id +
                ", ley=" + ley +
                ", tipoContrato='" + tipoContrato + '\'' +
                '}';
    }
}
