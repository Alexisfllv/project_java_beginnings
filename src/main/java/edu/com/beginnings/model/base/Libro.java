package edu.com.beginnings.model.base;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "libros")
public class Libro {


    //datos de tabla libros
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "libro_titulo", length = 60 , nullable = true)
    private String titulo;

    @Column(name = "libro_autor", length = 80, nullable = false)
    private String autor;

    @Column(name = "libro_fecha_publicacion", nullable = false)
    private LocalDate fechaPublicacion;
}
