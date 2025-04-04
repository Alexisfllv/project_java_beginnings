package edu.com.beginnings.dto.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {

    private Integer id;
    private String titulo;
    private String autor;
    private LocalDate fechaPublicacion;
}
