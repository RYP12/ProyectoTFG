package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.NivelEnum;
import lombok.Data;

@Data
public class NivelDTO {
    private NivelEnum nivel;
    private Integer descuento;
}
