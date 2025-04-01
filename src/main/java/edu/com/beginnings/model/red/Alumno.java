package edu.com.beginnings.model.red;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alumno_id")
    private Integer id;

    @Column(name = "alumno_nombre")
    private String nombre;

    @OneToMany(mappedBy = "alumno" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlumnoCurso> alumnoCursos = new ArrayList<>();

    //relacion teciaria
    @ManyToMany
    @JoinTable(
            name = "alumno_taller",
            joinColumns = @JoinColumn(name = "alumno_id"),
            inverseJoinColumns = @JoinColumn(name = "taller_id")
    )
    private List<Taller> talleres = new ArrayList<>();

}
