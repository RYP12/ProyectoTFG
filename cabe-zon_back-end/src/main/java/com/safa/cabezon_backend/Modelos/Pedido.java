package com.safa.cabezon_backend.Modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "pedido", catalog = "cabezon", schema = "cabezon")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fecha_estimada_entrega")
    private Date fechaEstimada;

    @Column(name = "fecha_entrega")
    private Date fechaEntrega;

    @Column(name = "estado")
    private Estado estado;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "precio_total")
    private Double precioTotal;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
