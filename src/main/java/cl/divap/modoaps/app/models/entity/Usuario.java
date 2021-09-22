package cl.divap.modoaps.app.models.entity;

import cl.divap.modoaps.app.validation.annotation.PasswordRegexAnnotation;
import cl.divap.modoaps.app.validation.annotation.RunRegexAnnotation;
import cl.divap.modoaps.app.validation.annotation.ValidaRutAnnotation;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class Usuario implements Serializable {

    private static final long serialVersionUID = -8757031993536151021L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 80, message="El campo nombres debe tener entre {min} and {max} caracteres")
    @Column(length = 80, nullable=false)
    private String nombre;

    @NotBlank
    @Size(min = 2, max = 80, message="El campo apellido paterno debe tener entre {min} and {max} caracteres")
    @Column(name = "apellido_paterno", length = 80, nullable=false)
    private String apellidoPaterno;

    @NotBlank
    @Size(min = 2, max = 80, message="El campo apellido materno debe tener entre {min} and {max} caracteres")
    @Column(name = "apellido_materno", length = 80, nullable=false)
    private String apellidoMaterno;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Sexo sexo;

    @Column(length = 12, nullable=false, unique = true)
    private String run;

    @NotBlank
    @Email(message = "Email con formato inválido")
    @Size(min = 3, max = 50)
    @Column(length = 50, nullable=false, unique = true)
    private String email;

    @NotBlank
    @Size(min = 9, max = 9)
    @Column(length = 9, nullable=false)
    private String telefono;

    @PastOrPresent
    @NotNull
    @Column(name = "fecha_nacimiento", nullable=false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private Nacionalidad nacionalidad;

    // @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private ServicioSalud servicioSalud;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    private RolePerfil rolePerfil;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_comuna", referencedColumnName = "codigo_comuna") // Referencia al campo codigo_comuna de la clase (tabla) Comuna, por defecto sería el campo id si no colocamos el referencedColumnName
    private Comuna comuna;

    @NotBlank
    @Size(min = 9, max = 10)
    @RunRegexAnnotation
    @ValidaRutAnnotation
    @Column(length = 10, unique = true, nullable=false)
    private String username;

    // @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Solo se permiten letras y/o números")
    // @PasswordRegexAnnotation
    // @NotBlank
    // @Size(min = 8, max = 80)
    @Column(length = 80, nullable=false)
    private String password;

    // @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Solo se permiten letras y/o números")
    // @PasswordRegexAnnotation
    // @NotBlank
    // @Size(min = 8, max = 20)
    @Transient
    private String confirmPassword;

    @Column(columnDefinition = "boolean default true", nullable=false)
    private Boolean enabled;

    @Column(columnDefinition = "boolean default true", nullable=true)
    private Boolean isOnline;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Role> roles;

    @Column(columnDefinition = "integer default 0", nullable=false)
    private Integer intentos;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lockout_end", nullable=true, columnDefinition = "timestamp default null")
    private Date lockoutEnd;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_at", nullable=false)
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_visit_date ", nullable=true, columnDefinition = "timestamp default null")
    private Date lastVisitDate;

    public Usuario() {
        this.roles = new ArrayList<Role>();
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public RolePerfil getRolePerfil() {
        return rolePerfil;
    }

    public void setRolePerfil(RolePerfil rolePerfil) {
        this.rolePerfil = rolePerfil;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Date getLockoutEnd() {
        return lockoutEnd;
    }

    public void setLockoutEnd(Date lockoutEnd) {
        this.lockoutEnd = lockoutEnd;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public ServicioSalud getServicioSalud() {
        return servicioSalud;
    }

    public void setServicioSalud(ServicioSalud servicioSalud) {
        this.servicioSalud = servicioSalud;
    }

    public Comuna getComuna() {
        return comuna;
    }

    public void setComuna(Comuna comuna) {
        this.comuna = comuna;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public Date getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(Date lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", sexo=" + sexo +
                ", run='" + run + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", nacionalidad=" + nacionalidad +
                ", servicioSalud=" + servicioSalud +
                ", rolePerfil=" + rolePerfil +
                ", comuna=" + comuna +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", enabled=" + enabled +
                ", isOnline=" + isOnline +
                ", roles=" + roles +
                ", intentos=" + intentos +
                ", lockoutEnd=" + lockoutEnd +
                ", createAt=" + createAt +
                ", lastVisitDate=" + lastVisitDate +
                '}';
    }
}
