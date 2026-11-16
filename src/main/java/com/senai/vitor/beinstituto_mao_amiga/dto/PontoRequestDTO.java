package com.senai.vitor.beinstituto_mao_amiga.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PontoRequestDTO(
        @Schema(example = "Ponto Mão Amiga - Setor Bueno")
        @NotBlank(message = "nome é obrigatório")
        @Size(max = 100, message = "nome deve ter no máximo 100 caracteres")
        String nome,

        @Schema(example = "Av. T-10, 1200")
        @NotBlank(message = "endereco é obrigatório")
        @Size(max = 200, message = "endereco deve ter no máximo 200 caracteres")
        String endereco,

        @Schema(example = "Setor Bueno")
        @NotBlank(message = "bairro é obrigatório")
        @Size(max = 100, message = "bairro deve ter no máximo 100 caracteres")
        String bairro,

        @Schema(example = "Seg a Sex, 8h às 17h")
        @NotBlank(message = "horario é obrigatório")
        @Size(max = 100, message = "horario deve ter no máximo 100 caracteres")
        String horario,

        @Schema(example = "coleta", allowableValues = {"coleta", "distribuição"})
        @NotBlank(message = "tipo é obrigatório")
        @Pattern(regexp = "(?iu)coleta|distribui[çc][ãa]o",
                message = "tipo deve ser 'coleta' ou 'distribuição'")
        String tipo
) {
}
