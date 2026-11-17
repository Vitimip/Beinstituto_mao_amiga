package com.senai.vitor.beinstituto_mao_amiga.service;

import com.senai.vitor.beinstituto_mao_amiga.dto.DoacaoRequestDTO;
import com.senai.vitor.beinstituto_mao_amiga.dto.DoacaoResponseDTO;
import com.senai.vitor.beinstituto_mao_amiga.entity.Doacao;
import com.senai.vitor.beinstituto_mao_amiga.repository.DoacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoacaoService {

    private final DoacaoRepository doacaoRepository;

    public List<DoacaoResponseDTO> findAll() {
        return doacaoRepository.findAll()
                .stream()
                .map(DoacaoResponseDTO::toDto)
                .toList();
    }

    public Page<DoacaoResponseDTO> findByFiltro(String filtro, Pageable pageable) {
        String termo = (filtro == null) ? "" : filtro.trim();
        return doacaoRepository.findByFiltro(termo, pageable)
                .map(DoacaoResponseDTO::toDto);
    }

    public DoacaoResponseDTO findById(UUID id) {
        return DoacaoResponseDTO.toDto(buscarOuFalhar(id));
    }

    public DoacaoResponseDTO save(DoacaoRequestDTO dto) {
        Doacao doacao = new Doacao();
        preencher(doacao, dto);
        doacao.setCriadoEm(LocalDateTime.now());
        return DoacaoResponseDTO.toDto(doacaoRepository.save(doacao));
    }

    public DoacaoResponseDTO update(UUID id, DoacaoRequestDTO dto) {
        Doacao doacao = buscarOuFalhar(id);
        preencher(doacao, dto);
        return DoacaoResponseDTO.toDto(doacaoRepository.save(doacao));
    }

    public void delete(UUID id) {
        doacaoRepository.delete(buscarOuFalhar(id));
    }

    private Doacao buscarOuFalhar(UUID id) {
        return doacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Doação não encontrada: " + id));
    }

    private void preencher(Doacao doacao, DoacaoRequestDTO dto) {
        doacao.setTipoItem(dto.tipoItem().trim());
        doacao.setQuantidade(dto.quantidade());
        doacao.setPontoDestino(dto.pontoDestino().trim());
    }
}
