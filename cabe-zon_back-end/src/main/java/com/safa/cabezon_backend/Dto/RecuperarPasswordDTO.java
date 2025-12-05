package com.safa.cabezon_backend.Dto;

import lombok.Data;

@Data
public class RecuperarPasswordDTO {
    private String token;
    private String nuevaPassword;
}