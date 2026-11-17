package com.senai.vitor.beinstituto_mao_amiga.repository;

import com.senai.vitor.beinstituto_mao_amiga.entity.Doacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, UUID> {

    @Query("""
            SELECT d
            FROM Doacao d
            WHERE UPPER(d.tipoItem) LIKE CONCAT('%', UPPER(:filtro), '%')
            """)
    Page<Doacao> findByFiltro(@Param("filtro") String filtro, Pageable pageable);
}
