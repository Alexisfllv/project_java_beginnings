package edu.com.beginnings.dto.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDTO {
    private Integer id;
    private String nombre;
    private Integer cantidad;
    private BigDecimal peso;
}
