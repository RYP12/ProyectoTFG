package com.safa.cabezon_backend.Modelos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "resenya_cliente",catalog = "cabezon",schema = "cabezon")
public class ResenyaCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "texto",length = 100,nullable = false)
    private String texto;

    @Column(name = "valoracion",length = 100,nullable = false)
    private Integer valoracion;

    @Column(name = "fecha",length = 100,nullable = false)
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;


}
