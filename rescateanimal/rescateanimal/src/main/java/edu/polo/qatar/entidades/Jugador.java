package edu.polo.qatar.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper=true)
@Table(name="jugadores")
public class Jugador extends Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @NotNull(message = "Debe elegir un valor")
    private Seleccion seleccion;

    private String foto;

    public Jugador(String nombre, Date fechaNacimiento, Pais lugarDeNacimiento) {
        super(nombre, fechaNacimiento, lugarDeNacimiento);
    }

    public Jugador(Long id, Seleccion seleccion, String foto, String nombre, Date fechaNacimiento, Pais lugarDeNacimiento) {
        super(nombre, fechaNacimiento, lugarDeNacimiento);
        this.id = id;
        this.seleccion = seleccion;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Pais getLugarDeNacimiento() {
        return lugarDeNacimiento;
    }

    public void setLugarDeNacimiento(Pais lugarDeNacimiento) {
        this.lugarDeNacimiento = lugarDeNacimiento;
    }
}