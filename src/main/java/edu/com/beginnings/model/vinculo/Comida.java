package edu.com.beginnings.model.vinculo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comidas")
public class Comida {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comida_id")
    private Integer id;

    @Column(name = "comida_nombre",nullable = false)
    private String nombre;

    @Column(name = "producto_cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "producto_peso", precision = 10, scale = 3, nullable = false)
    private BigDecimal peso;

    @Column(name = "producto_fecha_inicio", nullable = true)
    private LocalDateTime fechaInicio;

    @Column(name = "producto_fecha_fin", nullable = true)
    private LocalDateTime fechaFin;

    //fk
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;


}
