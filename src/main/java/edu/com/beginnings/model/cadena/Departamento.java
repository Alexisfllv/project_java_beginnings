package edu.com.beginnings.model.cadena;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departamentos")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "departamento_id")
    private Integer id;

    @Column(name = "departamento_nombre",nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "empresa_id",nullable = false)
    private Empresa empresa;



}
