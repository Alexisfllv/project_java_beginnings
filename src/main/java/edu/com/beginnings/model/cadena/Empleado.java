package edu.com.beginnings.model.cadena;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empleado_id" )
    private Integer id;

    @Column(name = "empleado_nombre",nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "departamento_id",nullable = false)
    private Departamento departamento;


}
