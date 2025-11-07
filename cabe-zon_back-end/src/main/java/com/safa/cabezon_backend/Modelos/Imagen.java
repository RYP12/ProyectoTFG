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
@Table(name = "imagen",catalog = "cabezon",schema = "cabezon")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre",length = 100,nullable = false)
    private String nombre;

    @Column(name = "url",length = 500,nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_producto",nullable = false)
    private Producto producto;


}
