package com.senai.vitor.beinstituto_mao_amiga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record DoacaoRequestDTO(
        @Schema(example = "Roupas de inverno")
        @NotBlank(message = "tipoItem é obrigatório")
        String tipoItem,

        @Schema(example = "10")
        @Min(value = 1, message = "quantidade deve ser maior que zero")
        int quantidade,

        @Schema(example = "Ponto Mão Amiga - Setor Bueno")
        @NotBlank(message = "pontoDestino é obrigatório")
        String pontoDestino
) {
}
