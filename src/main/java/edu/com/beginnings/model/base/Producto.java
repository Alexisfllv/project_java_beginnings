package edu.com.beginnings.model.base;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos_refrigerados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    //
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "producto_nombre",length = 60, nullable = false)
    private String nombre;

    @Column(name = "producto_cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "producto_peso", precision = 10, scale = 3, nullable = false)
    private BigDecimal peso;

    @Column(name = "producto_fecha_inicio",nullable = true)
    private LocalDateTime fechaInicio;


    @Column(name = "producto_fecha_fin", nullable = true)
    private LocalDateTime fechaFin;

    @PrePersist
    public void prePersist() {
        if (fechaInicio == null) {
            fechaInicio = LocalDateTime.now(); // Asigna la fecha actual si no se ha definido
        }
        if (fechaFin == null) {
            fechaFin = fechaInicio.plusDays(3); // Suma 3 días automáticamente
        }

    }
}
