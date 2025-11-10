package com.safa.cabezon_backend.Modelos;

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
public class ProductoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subtotal",length = 50,nullable = false)
    private Double subtotal;

    @Column(name = "cantidad",length = 5,nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;
}
