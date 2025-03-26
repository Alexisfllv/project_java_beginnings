package edu.com.beginnings.model.vinculo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer id;


    @Column(name = "cliente_nombre" , nullable = false)
    private String nombre;

    @Column(name = "cliente_email", nullable = false)
    private String email;

    @Column(name = "cliente_telefono", nullable = false)
    private Integer telefono;



}
