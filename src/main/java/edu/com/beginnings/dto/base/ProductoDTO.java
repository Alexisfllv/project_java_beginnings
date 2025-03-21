package edu.com.beginnings.dto.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Integer id;
    private String nombre;
    private Integer cantidad;
    private BigDecimal peso;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
