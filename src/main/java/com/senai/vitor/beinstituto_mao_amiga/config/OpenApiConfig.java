package com.senai.vitor.beinstituto_mao_amiga.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Instituto Mão Amiga - API de Pontos",
        version = "v1",
        description = "CRUD de pontos de coleta e distribuição em Goiânia"))
public class OpenApiConfig {
}
