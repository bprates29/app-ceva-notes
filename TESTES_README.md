# Testes Unitários - RotuloController

## Descrição
Este projeto inclui testes unitários abrangentes para o `RotuloController` usando JUnit 5, Mockito e Javalin Test Tools.

## Testes Implementados

### 1. **Testes de Listagem (GET /rotulos)**
- ✅ `deveListarTodosOsRotulos()` - Valida o retorno de lista com múltiplos rótulos
- ✅ `deveRetornarArrayVazioQuandoNaoHouverRotulos()` - Verifica retorno de array vazio quando não há dados

### 2. **Testes de Busca por ID (GET /rotulos/{id})**
- ✅ `deveBuscarRotuloPorId()` - Valida busca de rótulo específico por ID
- ✅ `deveRetornar404AoBuscarRotuloInexistente()` - Verifica erro 404 para ID inexistente
- ✅ `deveRetornar400ComIdInvalido()` - Valida erro 400 para ID inválido (não numérico)

### 3. **Testes de Criação (POST /rotulos)**
- ✅ `deveCriarNovoRotulo()` - Valida criação com status 201 e dados corretos
- ✅ `deveRetornar400AoCriarRotuloComNomeVazio()` - Valida erro para nome vazio/nulo
- ✅ `deveRetornar400AoCriarRotuloComEstiloVazio()` - Valida erro para estilo vazio/nulo
- ✅ `deveRetornar400AoCriarRotuloComTeorAlcoolicoNegativo()` - Valida erro para teor alcoólico negativo

### 4. **Testes de Atualização (PUT /rotulos/{id})**
- ✅ `deveAtualizarRotulo()` - Valida atualização com status 200 e dados corretos

### 5. **Testes de Remoção (DELETE /rotulos/{id})**
- ✅ `deveDeletarRotulo()` - Valida remoção com status 204
- ✅ `deveRetornar404AoDeletarRotuloInexistente()` - Verifica erro 404 ao deletar ID inexistente

### 6. **Teste de Fluxo Completo**
- ✅ `deveValidarFluxoCompletoDeOperacoes()` - Simula fluxo completo: listar vazio → criar → listar com item → buscar

## Tecnologias Utilizadas

- **JUnit 5** - Framework principal de testes
- **Mockito** - Para criar mocks do `RotuloService`
- **Javalin Test Tools** - Para testes de integração HTTP
- **Jackson** - Para serialização/deserialização JSON
- **Assertions** - Para validações dos resultados

## Como Executar os Testes

### Executar todos os testes do RotuloController:
```bash
./gradlew test --tests "*RotuloControllerTest*"
```

### Executar com limpeza (força nova execução):
```bash
./gradlew clean test --tests "*RotuloControllerTest*"
```

### Executar todos os testes do projeto:
```bash
./gradlew test
```

### Ver relatório detalhado:
```bash
./gradlew test --info
```

## Resultados dos Testes

✅ **13 testes executados**  
✅ **0 falhas**  
✅ **0 erros**  
✅ **0 testes ignorados**  

**Tempo total de execução: ~1.3 segundos**

## Cobertura de Cenários

Os testes cobrem:
- ✅ Casos de sucesso para todas as operações CRUD
- ✅ Validações de entrada (dados obrigatórios, formatos)
- ✅ Tratamento de erros (404, 400)
- ✅ Códigos de status HTTP corretos
- ✅ Serialização/deserialização JSON
- ✅ Integração com o service layer através de mocks
- ✅ Fluxo completo de operações

## Estrutura dos Testes

```java
@ExtendWith(MockitoExtension.class)
class RotuloControllerTest {
    @Mock private RotuloService rotuloService;
    private RotuloController rotuloController;
    private ObjectMapper objectMapper; // Para JSON
    
    // Métodos auxiliares para criar mocks e DTOs
    // Testes individuais para cada endpoint
    // Validações de status codes e conteúdo das respostas
}
```

## Padrões Seguidos

- **AAA Pattern**: Arrange, Act, Assert
- **Mocking**: Service layer mockado para isolamento
- **Test Data Builders**: Métodos auxiliares para criar objetos de teste
- **Assertions**: Validações claras com mensagens descritivas
- **Naming**: Nomes descritivos que explicam o comportamento testado 