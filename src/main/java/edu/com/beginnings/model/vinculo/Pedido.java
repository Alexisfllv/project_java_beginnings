package edu.com.beginnings.model.vinculo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Integer id;

    @Column(name = "pedido_fecha", nullable = true)
    private LocalDate fecha;

    @Column(name = "producto_total", precision = 10, scale = 3, nullable = false)
    private BigDecimal total;

    @Column(name = "pedido_estado", nullable = false)
    private String estado;

    // M:1
    @ManyToOne
    @JoinColumn(name = "cliente_id" , nullable = false)
    private Cliente cliente;

    // M:1
    @ManyToOne
    @JoinColumn(name = "suministro_id" , nullable = false)
    private Suministro suministro;


}
