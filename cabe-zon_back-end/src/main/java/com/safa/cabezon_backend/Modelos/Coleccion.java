package com.safa.cabezon_backend.Modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor

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



}
