package com.safa.cabezon_backend.Modelos;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "nivel", catalog = "cabezon", schema = "cabezon")
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nivel")
    private NivelEnum nivel;

    @Column(name = "descuento")
    private Integer descuento;
}
