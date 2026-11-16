package com.senai.vitor.beinstituto_mao_amiga.controller;

import com.senai.vitor.beinstituto_mao_amiga.dto.ErroResponseDTO;
import com.senai.vitor.beinstituto_mao_amiga.dto.PontoRequestDTO;
import com.senai.vitor.beinstituto_mao_amiga.dto.PontoResponseDTO;
import com.senai.vitor.beinstituto_mao_amiga.service.PontoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pontos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Pontos", description = "CRUD de pontos de coleta e distribuição")
public class PontoController {

    private final PontoService pontoService;

    @Operation(summary = "Lista todos os pontos")
    @ApiResponse(responseCode = "200", description = "Lista de pontos")
    @GetMapping
    public List<PontoResponseDTO> findAll() {
        return pontoService.findAll();
    }

    // GET /api/v1/pontos/filtro?filtro=bueno&page=0&size=10&sort=bairro,asc
    @Operation(summary = "Busca pontos por nome ou bairro, com paginação",
            description = "Sem 'filtro' devolve todos os pontos paginados. Padrão: 10 itens por página, ordenados por nome. "
                    + "Use sort=bairro,asc (ou nome,desc etc.) para trocar a ordem.")
    @ApiResponse(responseCode = "200", description = "Página de pontos (content + page)")
    @GetMapping("/filtro")
    public Page<PontoResponseDTO> findByFiltro(
            @RequestParam(defaultValue = "") String filtro,
            @ParameterObject @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return pontoService.findByFiltro(filtro, pageable);
    }

    @Operation(summary = "Busca um ponto pelo id")
    @ApiResponse(responseCode = "200", description = "Ponto encontrado")
    @ApiResponse(responseCode = "404", description = "Ponto não encontrado",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @GetMapping("/{id}")
    public PontoResponseDTO findById(@PathVariable UUID id) {
        return pontoService.findById(id);
    }

    @Operation(summary = "Cadastra um ponto")
    @ApiResponse(responseCode = "201", description = "Ponto criado")
    @ApiResponse(responseCode = "400", description = "Campo obrigatório vazio ou inválido",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PontoResponseDTO save(@Valid @RequestBody PontoRequestDTO dto) {
        return pontoService.save(dto);
    }

    @Operation(summary = "Atualiza um ponto")
    @ApiResponse(responseCode = "200", description = "Ponto atualizado")
    @ApiResponse(responseCode = "400", description = "Campo obrigatório vazio ou inválido",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Ponto não encontrado",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @PutMapping("/{id}")
    public PontoResponseDTO update(@PathVariable UUID id, @Valid @RequestBody PontoRequestDTO dto) {
        return pontoService.update(id, dto);
    }

    @Operation(summary = "Remove um ponto")
    @ApiResponse(responseCode = "204", description = "Ponto removido")
    @ApiResponse(responseCode = "404", description = "Ponto não encontrado",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        pontoService.delete(id);
    }
}
