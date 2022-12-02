package edu.polo.qatar.entidades;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.*;
import javax.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="selecciones")
public class Seleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "seleccion")
    @JsonManagedReference
    private List<Jugador> jugadores;

    @Override
    public String toString() {
        return this.nombre;
    }
    
}