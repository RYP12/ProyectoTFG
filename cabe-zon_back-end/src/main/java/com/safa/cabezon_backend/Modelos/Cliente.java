package com.safa.cabezon_backend.Modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"pedidos", "ListaDeseosSet", "interesesSet"})
@EqualsAndHashCode(exclude = {"pedidos", "ListaDeseosSet", "interesesSet"})
@Entity
@Table(name = "cliente", catalog = "cabezon", schema = "cabezon")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "foto")
    private String foto;

    @Column(name = "cabecoins")
    private Integer cabecoins;

    @ManyToOne
    @JoinColumn(name = "id_nivel")
    private Nivel nivel;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "gustos",catalog = "cabezon",schema = "cabezon",
            joinColumns = {@JoinColumn(name = "id_cliente",nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_producto",nullable = false)})
    private Set<Producto> ListaDeseosSet = new HashSet<>(0);

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "intereses",catalog = "cabezon",schema = "cabezon",
            joinColumns = {@JoinColumn(name = "id_cliente",nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "id_coleccion",nullable = false)})
    private Set<Coleccion> interesesSet = new HashSet<>(0);

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente",fetch = FetchType.EAGER)
    private Set<Pedido> pedidos;

    @OneToOne
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuario usuario;
}
