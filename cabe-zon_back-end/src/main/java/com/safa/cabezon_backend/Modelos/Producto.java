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
@Table(name = "producto",catalog = "cabezon",schema = "cabezon")
public class Producto{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre",length = 100,nullable = false)
    private String nombre;

    @Column(name = "descripcion",length = 500,nullable = false)
    private String descripcion;

    @Column(name = "precio",length = 50,nullable = false)
    private Double precio;

    @Column(name = "codigo_producto",length = 5,nullable = false)
    private Integer codigoProducto;

    @Column(name = "stock",length = 10,nullable = false)
    private Integer stock;

    @Column(name = "exclusivo",nullable = false)
    private Boolean exclusivo;

    @Column(name = "valoracion",nullable = false)
    private Double valoracion;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "imagen")
    private Set<Imagen> imagenes = new HashSet<>(0);

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "coleccion_producto",catalog = "cabezon",schema = "cabezon",
            joinColumns = {@JoinColumn(name = "id_producto",nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_coleccion",nullable = false)})
    private Set<Coleccion> coleccionesSet = new HashSet<>(0);


}
