package com.senai.vitor.beinstituto_mao_amiga.controller;

import com.senai.vitor.beinstituto_mao_amiga.dto.DoacaoRequestDTO;
import com.senai.vitor.beinstituto_mao_amiga.dto.DoacaoResponseDTO;
import com.senai.vitor.beinstituto_mao_amiga.dto.ErroResponseDTO;
import com.senai.vitor.beinstituto_mao_amiga.service.DoacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Doações", description = "CRUD de doações")
public class DoacaoController {

    private final DoacaoService doacaoService;

    // GET /api/v1/doacoes/filtro?filtro=roupa&page=0&size=10
    @Operation(summary = "Busca doações por tipo de item, com paginação",
            description = "Filtra pelo tipo de item, sem diferenciar maiúsculas, da mais nova para a mais antiga. "
                    + "Sem 'filtro' devolve todas as doações paginadas.")
    @ApiResponse(responseCode = "200", description = "Página de doações (content + page)")
    @GetMapping("/filtro")
    public Page<DoacaoResponseDTO> findByFiltro(
            @RequestParam(defaultValue = "") String filtro,
            @ParameterObject @PageableDefault(sort = "criadoEm", direction = Sort.Direction.DESC, size = 10) Pageable pageable) {
        return doacaoService.findByFiltro(filtro, pageable);
    }

    @Operation(summary = "Busca uma doação pelo id")
    @ApiResponse(responseCode = "200", description = "Doação encontrada")
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @GetMapping("/{id}")
    public DoacaoResponseDTO findById(@PathVariable UUID id) {
        return doacaoService.findById(id);
    }

    @Operation(summary = "Cadastra uma doação")
    @ApiResponse(responseCode = "201", description = "Doação criada")
    @ApiResponse(responseCode = "400", description = "Campo obrigatório vazio ou inválido",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoacaoResponseDTO save(@Valid @RequestBody DoacaoRequestDTO dto) {
        return doacaoService.save(dto);
    }

    @Operation(summary = "Atualiza uma doação")
    @ApiResponse(responseCode = "200", description = "Doação atualizada")
    @ApiResponse(responseCode = "400", description = "Campo obrigatório vazio ou inválido",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @PutMapping("/{id}")
    public DoacaoResponseDTO update(@PathVariable UUID id, @Valid @RequestBody DoacaoRequestDTO dto) {
        return doacaoService.update(id, dto);
    }

    @Operation(summary = "Remove uma doação")
    @ApiResponse(responseCode = "204", description = "Doação removida")
    @ApiResponse(responseCode = "404", description = "Doação não encontrada",
            content = @Content(schema = @Schema(implementation = ErroResponseDTO.class)))
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        doacaoService.delete(id);
    }
}
