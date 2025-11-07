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
@Table(name = "direccion", catalog = "cabezon", schema = "cabezon")
public class Direcciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "calle")
    private String calle;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "piso")
    private String piso;

    @Column(name = "letra")
    private String letra;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    @Column(name = "adicional")
    private String adicional;

    @Column(name = "pais")
    private String pais;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "municipio")
    private String municipio;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
}
