package edu.com.beginnings.model.vinculo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Table(name = "suministro")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Suministro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suministro_id")
    private Integer id;

    @Column(name = "suministro_nombre" ,nullable = false)
    private String nombre;

    @Column(name = "suministro_ubicacion",nullable = false)
    private String ubicacion;

}
