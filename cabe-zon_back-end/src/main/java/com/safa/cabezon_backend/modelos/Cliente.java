package com.safa.cabezon_backend.modelos;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

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

    @Column(name = "correo")
    private String correo;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "foto")
    private String foto;

    @Column(name = "rol")
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "id_nivel")
    private Nivel nivel;

    @Column(name = "cabecoins")
    private Integer cabecoins;
}
