package com.senai.vitor.beinstituto_mao_amiga.repository;

import com.senai.vitor.beinstituto_mao_amiga.entity.Ponto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PontoRepository extends JpaRepository<Ponto, UUID> {

    boolean existsByNome(String nome);

    @Query("""
            SELECT p
            FROM Ponto p
            WHERE UPPER(p.nome) LIKE CONCAT('%', UPPER(:filtro), '%')
               OR UPPER(p.bairro) LIKE CONCAT('%', UPPER(:filtro), '%')
            """)
    Page<Ponto> findByFiltro(@Param("filtro") String filtro, Pageable pageable);
}
