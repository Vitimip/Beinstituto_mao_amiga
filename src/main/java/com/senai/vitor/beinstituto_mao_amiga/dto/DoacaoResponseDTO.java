package com.senai.vitor.beinstituto_mao_amiga.dto;

import com.senai.vitor.beinstituto_mao_amiga.entity.Doacao;

import java.time.LocalDateTime;
import java.util.UUID;

public record DoacaoResponseDTO(
        UUID id,
        String tipoItem,
        int quantidade,
        String pontoDestino,
        LocalDateTime criadoEm
) {
    public static DoacaoResponseDTO toDto(Doacao doacao) {
        return new DoacaoResponseDTO(
                doacao.getId(),
                doacao.getTipoItem(),
                doacao.getQuantidade(),
                doacao.getPontoDestino(),
                doacao.getCriadoEm()
        );
    }
}
