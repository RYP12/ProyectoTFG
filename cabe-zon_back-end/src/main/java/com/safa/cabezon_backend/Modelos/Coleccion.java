package com.safa.cabezon_backend.Modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

@Entity
@Table(name = "coleccion",catalog = "cabezon",schema = "cabezon")
public class Coleccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre",length = 100,nullable = false)
    private String nombre;

    @Column(name = "numero_de_productos",length = 100,nullable = false)
    private Integer numeroDeProductos;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "coleccion_producto",catalog = "cabezon",schema = "cabezon",
            joinColumns = {@JoinColumn(name = "id_coleccion",nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_producto",nullable = false)})
    private Set<Producto> productosColeccionSet = new HashSet<>(0);


}
