# Inventory Divergence Tracker — Plano do Projeto

## Contexto e Problema

Empresas com operação logística costumam descobrir divergências de estoque
tarde demais — geralmente só na auditoria periódica. O problema raramente é
"físico ≠ sistema" de forma simples: existem causas distintas por trás de
cada divergência, e um sistema que apenas aponta a diferença numérica é
limitado. Este projeto nasce de uma experiência real: recuperação de uma
divergência de R$ 400.000 em ambiente de operação logística (Atlas Schindler
Elevadores, usando SAP — MIGO, VL02N, VF01, VA02, MM01).

O objetivo é construir um sistema que não só identifique divergências, mas
**classifique a causa provável**, com base em regras de negócio derivadas de
padrões reais:

- Movimentação física ocorrida sem registro no sistema
- Erro de lançamento (quantidade ou item incorretos)
- Perda ou avaria não contabilizada
- Atraso entre a movimentação física e o registro digital (timing)

---

## Stack Técnica

- **Java** (versão a definir — 21 ou 25, conforme padrão dos outros projetos)
- **Spring Boot 3.x**
- **Spring Data JPA** + banco relacional (H2 em dev, PostgreSQL em produção)
- **Bean Validation** para validação de entrada
- **JUnit 5 + Mockito** para testes
- **Flyway** (opcional, para versionamento de schema)
- **Springdoc OpenAPI (Swagger)** para documentação
- **GitHub Actions** para CI (build + testes a cada push)
- **Deploy**: Railway

---

## Modelagem de Entidades (visão geral)

```
Warehouse       -> armazém / filial
StockItem       -> item de estoque (SKU, nome, categoria, quantidade teórica)
StockMovement   -> evento de movimentação (ENTRADA / SAÍDA / TRANSFERÊNCIA)
PhysicalCount   -> contagem física (data, quantidade contada, item, armazém)
Divergence      -> resultado da reconciliação (esperado x contado, causa, status)
```

Decisão de arquitetura: a quantidade atual de um `StockItem` não é apenas um
campo atualizado diretamente — ela é **derivada da soma dos eventos de
`StockMovement`** (padrão inspirado em event sourcing simplificado). Isso
cria um histórico auditável e reflete como sistemas reais de estoque/
financeiro costumam ser modelados.

---

## Funcionalidades

### Núcleo (MVP)
- [ ] Cadastro de itens de estoque (`StockItem`)
- [ ] Registro de movimentações (`StockMovement`): entrada, saída, transferência
- [ ] Registro de contagem física (`PhysicalCount`)
- [ ] Cálculo automático de divergência: teórico vs. contado

### Diferencial
- [ ] Classificação automática de causa provável da divergência
  (via `DivergenceCauseAnalyzer`, usando Strategy Pattern)
- [ ] Histórico auditável por item (quem alterou, quando, origem/destino)
- [ ] Relatório de reconciliação por período/armazém, com ranking dos itens
  mais divergentes

### Avançado (se houver tempo)
- [ ] Dashboard com KPI de acuracidade de estoque (% de itens sem divergência)
- [ ] Importação de planilha (CSV/Excel) simulando exportação do SAP
- [ ] Notificação automática quando divergência ultrapassa limite configurável
  (`@Scheduled`)

---

## Decisões Técnicas Relevantes (para discutir em entrevista)

- **Event sourcing simplificado**: quantidade do item é derivada de eventos
  imutáveis (`StockMovement`), não de um campo mutável direto.
- **Strategy Pattern** para classificação de causa: cada regra de suspeita
  (atraso de lançamento, erro operacional, etc.) é uma implementação de
  `DivergenceCauseAnalyzer`, permitindo extensão sem alterar o núcleo.
- **Separação DTO x Entity**: relatórios de reconciliação não expõem
  entidades JPA diretamente.
- **Tratamento de exceções centralizado** via `@ControllerAdvice`.

---

## Roadmap de Execução

### Etapa 1 — Modelagem e Fundação
Criar entidades, relacionamentos JPA e repositórios básicos. Configurar
migrations (Flyway, se aplicável). Objetivo: banco de dados consistente
antes de qualquer regra de negócio.

### Etapa 2 — Regra de Negócio Central
Implementar o cálculo de divergência (teórico vs. contado) e o
`DivergenceCauseAnalyzer` com pelo menos 2-3 estratégias de classificação.
Esta é a etapa que concentra o maior valor do projeto — não apressar.

### Etapa 3 — API REST
Endpoints CRUD para as entidades principais + endpoint de reconciliação
(`POST /reconciliations`) que dispara o cálculo. DTOs de entrada/saída bem
definidos, Bean Validation, tratamento de exceção via `@ControllerAdvice`.

### Etapa 4 — Testes
Cobertura com JUnit + Mockito, priorizando a lógica de classificação de
causa (regra de negócio, não apenas persistência). Meta: relatório de
cobertura (JaCoCo) visível no README.

### Etapa 5 — Deploy, Documentação e Polish
Swagger/OpenAPI configurado, README explicando o problema real e a conexão
com a experiência prática em logística, deploy no Railway, pipeline de
GitHub Actions rodando testes a cada push.

---

## Status Atual

- [ ] Etapa 1 — Modelagem e Fundação
- [ ] Etapa 2 — Regra de Negócio Central
- [ ] Etapa 3 — API REST
- [ ] Etapa 4 — Testes
- [ ] Etapa 5 — Deploy, Documentação e Polish