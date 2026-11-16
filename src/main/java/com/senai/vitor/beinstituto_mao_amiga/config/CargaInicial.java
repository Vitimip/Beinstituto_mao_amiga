package com.senai.vitor.beinstituto_mao_amiga.config;

import com.senai.vitor.beinstituto_mao_amiga.entity.Ponto;
import com.senai.vitor.beinstituto_mao_amiga.entity.Tipo;
import com.senai.vitor.beinstituto_mao_amiga.repository.PontoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class CargaInicial {

    @Bean
    CommandLineRunner popularPontos(PontoRepository pontoRepository) {
        return args -> {
            List<Ponto> pontos = List.of(
                    new Ponto("Mão Amiga - Setor Central", "Rua 3, 450", "Setor Central", "Seg a Sex, 8h às 17h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Setor Bueno", "Av. T-10, 1200", "Setor Bueno", "Seg a Sáb, 9h às 18h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Setor Marista", "Rua 1137, 230", "Setor Marista", "Seg a Sex, 8h às 16h", Tipo.Distribuicao),
                    new Ponto("Mão Amiga - Setor Oeste", "Rua 115, 85", "Setor Oeste", "Ter a Sáb, 9h às 17h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Jardim América", "Av. T-63, 980", "Jardim América", "Seg a Sex, 8h às 17h", Tipo.Distribuicao),
                    new Ponto("Mão Amiga - Campinas", "Av. Anhanguera, 5000", "Campinas", "Seg a Sáb, 8h às 18h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Vila Nova", "Rua 74, 310", "Vila Nova", "Seg a Sex, 9h às 16h", Tipo.Distribuicao),
                    new Ponto("Mão Amiga - Setor Universitário", "Av. Universitária, 1500", "Setor Universitário", "Seg a Sex, 7h às 19h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Jardim Goiás", "Av. Jamel Cecílio, 2600", "Jardim Goiás", "Sáb e Dom, 9h às 14h", Tipo.Distribuicao),
                    new Ponto("Mão Amiga - Setor Sul", "Rua 9, 700", "Setor Sul", "Seg a Sex, 8h às 17h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Setor Pedro Ludovico", "Av. T-7, 640", "Setor Pedro Ludovico", "Seg a Sex, 8h às 17h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Setor Aeroporto", "Av. Contorno, 1800", "Setor Aeroporto", "Seg a Sex, 9h às 17h", Tipo.Distribuicao),
                    new Ponto("Mão Amiga - Setor Leste Universitário", "Rua 244, 120", "Setor Leste Universitário", "Ter a Sáb, 8h às 16h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Parque Amazônia", "Av. Bela Vista, 3300", "Parque Amazônia", "Seg a Sex, 8h às 17h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Jardim Novo Mundo", "Av. Rio Verde, 1450", "Jardim Novo Mundo", "Seg a Sáb, 8h às 17h", Tipo.Distribuicao),
                    new Ponto("Mão Amiga - Vila Redenção", "Rua R-4, 210", "Vila Redenção", "Seg a Sex, 9h às 16h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Setor Coimbra", "Rua C-5, 75", "Setor Coimbra", "Seg a Sex, 8h às 17h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Faiçalville", "Av. Faiçalville, 2100", "Faiçalville", "Sáb, 8h às 13h", Tipo.Distribuicao),
                    new Ponto("Mão Amiga - Cidade Jardim", "Av. Cidade Jardim, 400", "Cidade Jardim", "Seg a Sex, 8h às 17h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Setor Nova Suíça", "Av. Nova Suíça, 560", "Setor Nova Suíça", "Seg a Sáb, 9h às 18h", Tipo.Distribuicao),
                    new Ponto("Mão Amiga - Setor Santa Genoveva", "Av. Santa Genoveva, 780", "Setor Santa Genoveva", "Seg a Sex, 8h às 16h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Jardim Europa", "Rua 10, 330", "Jardim Europa", "Seg a Sex, 9h às 17h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Vila Brasília", "Rua 8, 150", "Vila Brasília", "Ter a Sex, 8h às 16h", Tipo.Distribuicao),
                    new Ponto("Mão Amiga - Setor Norte Ferroviário", "Av. Independência, 2200", "Setor Norte Ferroviário", "Seg a Sex, 7h às 16h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Setor Bela Vista", "Rua B-2, 95", "Setor Bela Vista", "Seg a Sex, 8h às 17h", Tipo.Coleta),
                    new Ponto("Mão Amiga - Bueno Shopping", "Av. 85, 1500", "Setor Bueno", "Seg a Dom, 10h às 22h", Tipo.Distribuicao)
            );

            // O banco é gravado em arquivo: só cadastra os pontos que ainda não existem
            // (comparando pelo nome). Assim reiniciar a aplicação não duplica nada, e um banco
            // criado com a carga antiga recebe apenas os pontos novos.
            List<Ponto> novos = pontos.stream()
                    .filter(p -> !pontoRepository.existsByNome(p.getNome()))
                    .toList();

            if (novos.isEmpty()) {
                log.info("Carga inicial ignorada: os {} pontos já existem.", pontos.size());
                return;
            }

            pontoRepository.saveAll(novos);
            log.info("Carga inicial: {} pontos cadastrados.", novos.size());
        };
    }
}
