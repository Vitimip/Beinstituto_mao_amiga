package com.senai.vitor.beinstituto_mao_amiga.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErroResponseDTO(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        Map<String, String> campos
) {
}
