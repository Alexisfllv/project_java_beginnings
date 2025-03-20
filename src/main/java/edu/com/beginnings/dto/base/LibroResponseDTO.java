package edu.com.beginnings.dto.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponseDTO {

    //
    private Integer id;
    private String titulo;
    private String autor;
}
