package com.safa.cabezon_backend.Dto;

import com.safa.cabezon_backend.Modelos.NivelEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class NivelDTO {
    @NotNull
    private NivelEnum nivel;
    @NotNull
    @PositiveOrZero
    private Integer descuento;
}
