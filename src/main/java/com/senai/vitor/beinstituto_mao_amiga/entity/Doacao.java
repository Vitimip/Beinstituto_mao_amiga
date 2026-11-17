package com.senai.vitor.beinstituto_mao_amiga.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_DOACAO")
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String tipoItem;

    @Column
    private int quantidade;

    @Column
    private String pontoDestino;

    @Column
    private LocalDateTime criadoEm;


    public Doacao(){}

   public Doacao(String tipoItem, int quantidade, String pontoDestino){
       this.tipoItem = tipoItem;
        this.quantidade = quantidade;
        this.pontoDestino = pontoDestino;
   }
}
