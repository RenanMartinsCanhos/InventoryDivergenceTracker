<div align="center">

# Inventory Divergence Tracker

### Sistema de reconciliação de estoque com classificação automática de causas

[![Java](https://img.shields.io/badge/Java-21%2F25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Spring Data JPA](https://img.shields.io/badge/Spring%20Data-JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-data-jpa)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Production-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![JUnit5](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![Swagger](https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://swagger.io/)
[![Railway](https://img.shields.io/badge/Deploy-Railway-0B0D0E?style=for-the-badge&logo=railway&logoColor=white)](https://railway.app/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](LICENSE)

</div>

---

## Sobre o projeto

Empresas com operação logística costumam descobrir divergências de estoque tarde demais — geralmente só na auditoria periódica. E o problema raramente é "físico diferente do sistema" de forma simples: existem causas distintas por trás de cada divergência, e um sistema que apenas aponta a diferença numérica não resolve muita coisa na prática.

Esse projeto nasce de uma situação real: recuperação de uma divergência de R$ 400.000 em operação logística, na Atlas Schindler Elevadores, usando SAP (MIGO, VL02N, VF01, VA02, MM01). Na época, boa parte do trabalho foi justamente investigar *por que* a diferença existia — se era erro de lançamento, atraso entre a movimentação física e o registro digital, perda não contabilizada, entre outras causas.

A ideia aqui é construir um sistema que não só identifica a divergência, mas tenta classificar a causa mais provável, com base em regras derivadas desses padrões reais:

- movimentação física ocorrida sem registro no sistema
- erro de lançamento (quantidade ou item incorretos)
- perda ou avaria não contabilizada
- atraso entre a movimentação física e o registro digital

Não é um CRUD de estoque. A parte que importa é a regra de negócio por trás da classificação.

---

## Stack técnica

| Camada | Tecnologia |
|---|---|
| Linguagem | Java (21 ou 25) |
| Framework | Spring Boot 3.x |
| Persistência | Spring Data JPA, H2 em desenvolvimento, PostgreSQL em produção |
| Validação | Bean Validation |
| Testes | JUnit 5 + Mockito |
| Versionamento de schema | Flyway (opcional) |
| Documentação de API | Springdoc OpenAPI (Swagger) |
| CI | GitHub Actions (build e testes a cada push) |
| Deploy | Railway |

---

## Modelagem

```
Warehouse       -> armazém / filial
StockItem       -> item de estoque (SKU, nome, categoria, quantidade teórica)
StockMovement   -> evento de movimentação (entrada, saída, transferência)
PhysicalCount   -> contagem física (data, quantidade contada, item, armazém)
Divergence      -> resultado da reconciliação (esperado x contado, causa, status)
```

Algumas decisões de arquitetura valem explicar:

**A quantidade de um item não é um campo atualizado direto.** Ela é derivada da soma dos eventos de `StockMovement` — uma espécie de event sourcing simplificado. Isso mantém um histórico auditável e reflete melhor como sistemas reais de estoque costumam funcionar.

**Classificação de causa via Strategy Pattern.** Cada regra de suspeita (atraso de lançamento, erro operacional, etc.) é uma implementação separada de `DivergenceCauseAnalyzer`. Dá para adicionar novas regras sem tocar no núcleo do sistema.

**DTOs separados das entidades.** Os relatórios de reconciliação não expõem entidades JPA diretamente.

**Tratamento de exceções centralizado**, via `@ControllerAdvice`.

---

## Funcionalidades

**Núcleo (MVP)**
- [ ] Cadastro de itens de estoque
- [ ] Registro de movimentações (entrada, saída, transferência)
- [ ] Registro de contagem física
- [ ] Cálculo de divergência: teórico vs. contado

**Diferencial**
 Classificação automática de causa provável (`DivergenceCauseAnalyzer`)
 Histórico auditável por item (quem alterou, quando, origem/destino)
 Relatório de reconciliação por período/armazém, com ranking dos itens mais divergentes
 
---

## Roadmap

| Etapa | O que envolve | Status |
|---|---|:---:|
| 1. Modelagem e fundação | Entidades, relacionamentos JPA, repositórios, migrations | pendente |
| 2. Regra de negócio central | Cálculo de divergência + `DivergenceCauseAnalyzer` | pendente |
| 3. API REST | CRUD + endpoint de reconciliação, DTOs, Bean Validation | pendente |
| 4. Testes | JUnit + Mockito, foco na lógica de classificação | pendente |
| 5. Deploy e documentação | Swagger, README final, deploy no Railway, CI | pendente |

A etapa 2 é onde está o valor real do projeto — é a parte que não dá pra apressar, porque é regra de negócio, não só persistência de dados.

---

## Como rodar localmente

```bash
git clone https://github.com/RenanMartinsCanhos/inventory-divergence-tracker.git
cd inventory-divergence-tracker

# banco H2 em memória, perfil de desenvolvimento
./mvnw spring-boot:run

# testes
./mvnw test
```

A documentação da API fica em `/swagger-ui.html` depois de subir a aplicação.

---

## Autor

Renan Martins Canhos — estudante de Análise e Desenvolvimento de Sistemas (USJT), em formação como desenvolvedor backend. Trabalha atualmente com operações logísticas e SAP na Atlas Schindler Elevadores, experiência que deu origem a este projeto.

[LinkedIn](#) · [GitHub](#)

---

## Licença

Este projeto está sob a licença MIT — veja o arquivo [LICENSE](LICENSE).
