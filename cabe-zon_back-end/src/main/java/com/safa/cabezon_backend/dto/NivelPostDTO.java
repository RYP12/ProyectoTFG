package com.safa.cabezon_backend.dto;

import com.safa.cabezon_backend.modelos.NivelEnum;
import lombok.Data;

@Data
public class NivelPostDTO {
    private NivelEnum nivel;
    private Integer descuento;
}
