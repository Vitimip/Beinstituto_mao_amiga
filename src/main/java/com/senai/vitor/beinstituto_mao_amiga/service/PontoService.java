package com.senai.vitor.beinstituto_mao_amiga.service;

import com.senai.vitor.beinstituto_mao_amiga.dto.PontoRequestDTO;
import com.senai.vitor.beinstituto_mao_amiga.dto.PontoResponseDTO;
import com.senai.vitor.beinstituto_mao_amiga.entity.Ponto;
import com.senai.vitor.beinstituto_mao_amiga.entity.Tipo;
import com.senai.vitor.beinstituto_mao_amiga.repository.PontoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PontoService {

    private final PontoRepository pontoRepository;

    public List<PontoResponseDTO> findAll() {
        return pontoRepository.findAll()
                .stream()
                .map(PontoResponseDTO::toDto)
                .toList();
    }

    public Page<PontoResponseDTO> findByFiltro(String filtro, Pageable pageable) {
        String termo = (filtro == null) ? "" : filtro.trim();
        return pontoRepository.findByFiltro(termo, pageable)
                .map(PontoResponseDTO::toDto);
    }

    public PontoResponseDTO findById(UUID id) {
        return PontoResponseDTO.toDto(buscarOuFalhar(id));
    }

    public PontoResponseDTO save(PontoRequestDTO dto) {
        Ponto ponto = new Ponto();
        preencher(ponto, dto);
        return PontoResponseDTO.toDto(pontoRepository.save(ponto));
    }

    public PontoResponseDTO update(UUID id, PontoRequestDTO dto) {
        Ponto ponto = buscarOuFalhar(id);
        preencher(ponto, dto);
        return PontoResponseDTO.toDto(pontoRepository.save(ponto));
    }

    public void delete(UUID id) {
        pontoRepository.delete(buscarOuFalhar(id));
    }

    private Ponto buscarOuFalhar(UUID id) {
        return pontoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ponto não encontrado: " + id));
    }

    private void preencher(Ponto ponto, PontoRequestDTO dto) {
        ponto.setNome(dto.nome().trim());
        ponto.setEndereco(dto.endereco().trim());
        ponto.setBairro(dto.bairro().trim());
        ponto.setHorario(dto.horario().trim());
        ponto.setTipo(Tipo.from(dto.tipo()));
    }
}
