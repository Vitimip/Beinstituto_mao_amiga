package com.senai.vitor.beinstituto_mao_amiga.entity;

import java.text.Normalizer;
import java.util.Locale;

public enum Tipo {
    Coleta("coleta"),
    Distribuicao("distribuição");

    private final String label;

    Tipo(String label) {
        this.label = label;
    }

    /** Texto devolvido pela API: "coleta" ou "distribuição". */
    public String getLabel() {
        return label;
    }

    /** Converte o texto recebido (sem diferenciar maiúsculas/acentos) para o enum. */
    public static Tipo from(String valor) {
        if (valor == null) {
            throw new IllegalArgumentException("tipo é obrigatório");
        }
        String normalizado = Normalizer.normalize(valor.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
        return switch (normalizado) {
            case "coleta" -> Coleta;
            case "distribuicao" -> Distribuicao;
            default -> throw new IllegalArgumentException("tipo deve ser 'coleta' ou 'distribuição'");
        };
    }
}
