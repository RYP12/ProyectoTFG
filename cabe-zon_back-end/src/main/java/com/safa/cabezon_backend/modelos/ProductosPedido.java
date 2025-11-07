package com.safa.cabezon_backend.modelos;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "productos_pedido",catalog = "cabezon",schema = "cabezon")
public class ProductosPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "precio_total",length = 50,nullable = false)
    private Double precioTotal;

    @Column(name = "cantidad",length = 5,nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}
