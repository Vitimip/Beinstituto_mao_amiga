# Instituto Mão Amiga – API de Pontos de Coleta

API REST em Spring Boot para cadastrar, consultar, editar e remover **pontos de coleta e distribuição** do Instituto Mão Amiga, em Goiânia.

## Tecnologias

Java 17 · Spring Boot 4 · Spring Web · Spring Data JPA · H2 (em arquivo) · Lombok · springdoc-openapi (Swagger) · Bean Validation

## Como rodar

```bash
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| OpenAPI (JSON) | http://localhost:8080/v3/api-docs |
| Console do H2 | http://localhost:8080/h2-console |

Console do H2: JDBC URL `jdbc:h2:file:./data/db-api`, usuário `sa`, senha vazia.

O banco é gravado em arquivo (`./data/db-api.mv.db`). Na inicialização a aplicação cadastra 26 pontos em Goiânia que ainda não existam (comparando pelo nome), então reiniciar **não duplica**. Os nomes e endereços da carga são de exemplo. Para recomeçar do zero, apague a pasta `data/`.

## Estrutura

```
config/       CargaInicial, OpenApiConfig, GlobalExceptionHandler
controller/   PontoController
dto/          PontoRequestDTO, PontoResponseDTO (record), ErroResponseDTO
entity/       Ponto, Tipo
repository/   PontoRepository
service/      PontoService
```

## Modelo

| Campo | Tipo | Regras |
|---|---|---|
| `id` | UUID | gerado pela API |
| `nome` | texto | obrigatório, não vazio, até 100 caracteres |
| `endereco` | texto | obrigatório, não vazio, até 200 caracteres |
| `bairro` | texto | obrigatório, não vazio, até 100 caracteres |
| `horario` | texto | obrigatório, não vazio, até 100 caracteres. Ex.: `Seg a Sex, 8h às 17h` |
| `tipo` | texto | obrigatório: `coleta` ou `distribuição` |

O controller sempre devolve `PontoResponseDTO` (nunca a entidade).

## Endpoints

Base: `http://localhost:8080`

| Método | URL | Descrição | Respostas |
|---|---|---|---|
| GET | `/api/v1/pontos` | Lista todos os pontos | 200 |
| GET | `/api/v1/pontos/filtro?filtro=&page=0&size=10` | Busca paginada por nome ou bairro | 200 |
| GET | `/api/v1/pontos/{id}` | Busca um ponto | 200, **404** |
| POST | `/api/v1/pontos` | Cadastra um ponto | 201, **400** |
| PUT | `/api/v1/pontos/{id}` | Atualiza um ponto | 200, **400**, **404** |
| DELETE | `/api/v1/pontos/{id}` | Remove um ponto | 204, **404** |

Exemplo de corpo para POST/PUT:

```json
{
  "nome": "Mão Amiga - Setor Bueno",
  "endereco": "Av. T-10, 1200",
  "bairro": "Setor Bueno",
  "horario": "Seg a Sex, 8h às 17h",
  "tipo": "coleta"
}
```

Exemplo de erro 400 (campo vazio — nada é gravado):

```json
{
  "timestamp": "2026-11-10T20:30:00",
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "Dados inválidos",
  "campos": { "nome": "nome é obrigatório" }
}
```

Exemplo de erro 404:

```json
{
  "timestamp": "2026-11-10T20:30:00",
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Ponto não encontrado: 00000000-0000-0000-0000-000000000000",
  "campos": {}
}
```

CORS liberado para qualquer origem (`@CrossOrigin(origins = "*")`).

## Busca paginada (N2-2)

`GET /api/v1/pontos/filtro?filtro=...&page=0&size=10&sort=...`

| Parâmetro | Padrão | Descrição |
|---|---|---|
| `filtro` | vazio | texto procurado no **nome** ou no **bairro**, sem diferenciar maiúsculas de minúsculas. Vazio devolve todos |
| `page` | `0` | número da página (começa em 0) |
| `size` | `10` | itens por página |
| `sort` | `nome` | campo e direção, ex.: `sort=bairro,asc` ou `sort=nome,desc` |

A resposta é uma página no formato `content` + `page` (`spring.data.web.pageable.serialization-mode=via_dto`), com itens `PontoResponseDTO`. A carga inicial tem 26 pontos.

> Os `id` dos exemplos abaixo são ilustrativos: o UUID é gerado quando o banco é criado e muda a cada banco novo. O restante da resposta segue a carga inicial.

### 1. Sem filtro (padrão: 10 itens, ordenados por nome)

```
GET http://localhost:8080/api/v1/pontos/filtro
```

```json
{
  "content": [
    {"id": "03243930-7f59-57b3-93bd-1c1aaf2ae9b4", "nome": "Mão Amiga - Bueno Shopping", "endereco": "Av. 85, 1500", "bairro": "Setor Bueno", "horario": "Seg a Dom, 10h às 22h", "tipo": "distribuição"},
    {"id": "edd78744-e970-5a4b-8049-cd75463ee4ed", "nome": "Mão Amiga - Campinas", "endereco": "Av. Anhanguera, 5000", "bairro": "Campinas", "horario": "Seg a Sáb, 8h às 18h", "tipo": "coleta"},
    {"id": "8f0a3f73-0d2a-5cfb-b007-60cbb5740451", "nome": "Mão Amiga - Cidade Jardim", "endereco": "Av. Cidade Jardim, 400", "bairro": "Cidade Jardim", "horario": "Seg a Sex, 8h às 17h", "tipo": "coleta"},
    {"id": "72384a20-fa62-50cb-885d-e02a10d5ceef", "nome": "Mão Amiga - Faiçalville", "endereco": "Av. Faiçalville, 2100", "bairro": "Faiçalville", "horario": "Sáb, 8h às 13h", "tipo": "distribuição"},
    {"id": "cb8c6d5c-17fc-58b1-a519-277f5c147d61", "nome": "Mão Amiga - Jardim América", "endereco": "Av. T-63, 980", "bairro": "Jardim América", "horario": "Seg a Sex, 8h às 17h", "tipo": "distribuição"},
    {"id": "428bcd41-7cde-5e07-94d5-5f62c1dd25ab", "nome": "Mão Amiga - Jardim Europa", "endereco": "Rua 10, 330", "bairro": "Jardim Europa", "horario": "Seg a Sex, 9h às 17h", "tipo": "coleta"},
    {"id": "2ddf413a-0fc6-59c4-bc03-a52b729fe216", "nome": "Mão Amiga - Jardim Goiás", "endereco": "Av. Jamel Cecílio, 2600", "bairro": "Jardim Goiás", "horario": "Sáb e Dom, 9h às 14h", "tipo": "distribuição"},
    {"id": "1eefe474-e61a-5d4c-b863-4519e279afe8", "nome": "Mão Amiga - Jardim Novo Mundo", "endereco": "Av. Rio Verde, 1450", "bairro": "Jardim Novo Mundo", "horario": "Seg a Sáb, 8h às 17h", "tipo": "distribuição"},
    {"id": "16f4b261-a02b-5b40-84f5-a7a847fa1a31", "nome": "Mão Amiga - Parque Amazônia", "endereco": "Av. Bela Vista, 3300", "bairro": "Parque Amazônia", "horario": "Seg a Sex, 8h às 17h", "tipo": "coleta"},
    {"id": "245f42d7-494d-5421-81ff-3f842a6df289", "nome": "Mão Amiga - Setor Aeroporto", "endereco": "Av. Contorno, 1800", "bairro": "Setor Aeroporto", "horario": "Seg a Sex, 9h às 17h", "tipo": "distribuição"}
  ],
  "page": { "size": 10, "number": 0, "totalElements": 26, "totalPages": 3 }
}
```

### 2. Filtrando por bairro

```
GET http://localhost:8080/api/v1/pontos/filtro?filtro=bueno
```

```json
{
  "content": [
    {"id": "03243930-7f59-57b3-93bd-1c1aaf2ae9b4", "nome": "Mão Amiga - Bueno Shopping", "endereco": "Av. 85, 1500", "bairro": "Setor Bueno", "horario": "Seg a Dom, 10h às 22h", "tipo": "distribuição"},
    {"id": "28bdc352-3142-5a53-95e2-440a2e5ad8fd", "nome": "Mão Amiga - Setor Bueno", "endereco": "Av. T-10, 1200", "bairro": "Setor Bueno", "horario": "Seg a Sáb, 9h às 18h", "tipo": "coleta"}
  ],
  "page": { "size": 10, "number": 0, "totalElements": 2, "totalPages": 1 }
}
```

### 3. Segunda página (`page=1`, 5 itens por página)

```
GET http://localhost:8080/api/v1/pontos/filtro?page=1&size=5
```

```json
{
  "content": [
    {"id": "428bcd41-7cde-5e07-94d5-5f62c1dd25ab", "nome": "Mão Amiga - Jardim Europa", "endereco": "Rua 10, 330", "bairro": "Jardim Europa", "horario": "Seg a Sex, 9h às 17h", "tipo": "coleta"},
    {"id": "2ddf413a-0fc6-59c4-bc03-a52b729fe216", "nome": "Mão Amiga - Jardim Goiás", "endereco": "Av. Jamel Cecílio, 2600", "bairro": "Jardim Goiás", "horario": "Sáb e Dom, 9h às 14h", "tipo": "distribuição"},
    {"id": "1eefe474-e61a-5d4c-b863-4519e279afe8", "nome": "Mão Amiga - Jardim Novo Mundo", "endereco": "Av. Rio Verde, 1450", "bairro": "Jardim Novo Mundo", "horario": "Seg a Sáb, 8h às 17h", "tipo": "distribuição"},
    {"id": "16f4b261-a02b-5b40-84f5-a7a847fa1a31", "nome": "Mão Amiga - Parque Amazônia", "endereco": "Av. Bela Vista, 3300", "bairro": "Parque Amazônia", "horario": "Seg a Sex, 8h às 17h", "tipo": "coleta"},
    {"id": "245f42d7-494d-5421-81ff-3f842a6df289", "nome": "Mão Amiga - Setor Aeroporto", "endereco": "Av. Contorno, 1800", "bairro": "Setor Aeroporto", "horario": "Seg a Sex, 9h às 17h", "tipo": "distribuição"}
  ],
  "page": { "size": 5, "number": 1, "totalElements": 26, "totalPages": 6 }
}
```

Para trocar a ordem, use `sort`, por exemplo: `GET http://localhost:8080/api/v1/pontos/filtro?sort=bairro,asc`.

## Prints do Swagger

Cada print mostra a requisição e a resposta no Swagger UI (`/swagger-ui/index.html`). Os arquivos ficam em `docs/prints/`.

| Endpoint | Resposta | Print |
|---|---|---|
| `GET /api/v1/pontos` | 200 | ![GET lista 200](docs/prints/get-lista-200.png) |
| `GET /api/v1/pontos/filtro` | 200 | ![GET filtro 200](docs/prints/get-filtro-200.png) |
| `GET /api/v1/pontos/{id}` | 200 | ![GET por id 200](docs/prints/get-id-200.png) |
| `GET /api/v1/pontos/{id}` (id inexistente) | **404** | ![GET por id 404](docs/prints/get-id-404.png) |
| `POST /api/v1/pontos` | 201 | ![POST 201](docs/prints/post-201.png) |
| `POST /api/v1/pontos` (nome vazio) | **400** | ![POST 400](docs/prints/post-400.png) |
| `PUT /api/v1/pontos/{id}` | 200 | ![PUT 200](docs/prints/put-200.png) |
| `PUT /api/v1/pontos/{id}` (id inexistente) | **404** | ![PUT 404](docs/prints/put-404.png) |
| `DELETE /api/v1/pontos/{id}` | 204 | ![DELETE 204](docs/prints/delete-204.png) |

Como gerar: abra o Swagger UI, use **Try it out** → **Execute** em cada endpoint e tire o print da área "Responses". Para o 404 use o id `00000000-0000-0000-0000-000000000000`; para o 400 envie `"nome": ""`.

## Fora de escopo

Doações (N2-5) e usar a busca no app (N2-3).
