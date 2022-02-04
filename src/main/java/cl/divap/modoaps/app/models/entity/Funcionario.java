package cl.divap.modoaps.app.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "funcionarios")
public class Funcionario implements Serializable {

    private static final long serialVersionUID = -4647978051360837244L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50, message="El campo nombres debe tener entre {min} and {max} caracteres")
    @Column(length = 50, nullable=false)
    private String nombres;

    @NotBlank
    @Size(min = 2, max = 50, message="El campo apellido paterno debe tener entre {min} and {max} caracteres")
    @Column(name = "apellido_paterno", length = 50, nullable=false)
    private String apellidoPaterno;

    @NotBlank
    @Size(min = 2, max = 50, message="El campo apellido materno debe tener entre {min} and {max} caracteres")
    @Column(name = "apellido_materno", length = 50, nullable=false)
    private String apellidoMaterno;

    @NotBlank
    @Size(min = 9, max = 12)
    @Column(length = 12, nullable=false, unique = true)
    private String run;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Sexo sexo;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Nacionalidad nacionalidad;

    @PastOrPresent
    @NotNull
    @Column(name = "fecha_nacimiento", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    @Column(name = "create_at", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date createAt;

    @JsonManagedReference
    @OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)	// mappedBy = "funcionario" ==> campo de la relación en tabla contrato, en la tabla contrato crea el campo funcionario
    private List<Contrato> contratos;

    @Column(nullable=false)
    private Boolean enabled;

    public Funcionario(String nombres, String apellidoPaterno, String apellidoMaterno, String run, Sexo sexo, Nacionalidad nacionalidad, Date fechaNacimiento, Date createAt, Boolean enabled) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.run = run;
        this.sexo = sexo;
        this.nacionalidad = nacionalidad;
        this.fechaNacimiento = fechaNacimiento;
        this.createAt = createAt;
        this.enabled = enabled;
    }

    public Funcionario() {
        this.contratos = new ArrayList<Contrato>();
    }

    @PrePersist
    public void prePersist() {
        createAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public List<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<Contrato> contratos) {
        this.contratos = contratos;
    }

    public void addContrato(Contrato contrato) {
        this.contratos.add(contrato);
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /*
    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", run='" + run + '\'' +
                ", sexo=" + sexo +
                ", nacionalidad=" + nacionalidad +
                ", fechaNacimiento=" + fechaNacimiento +
                ", createAt=" + createAt +
                '}';
    }
     */

    /*
    @Override
    public String toString() {
        return  nombres  + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    */
}
