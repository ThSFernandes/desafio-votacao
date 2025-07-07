# 🗳️ API REST - Desafio Votação


## 📌 Objetivo

Esta API RESTful simula o processo de votação em assembleias de cooperativas, onde cada associado possui direito a um voto. O projeto foi desenvolvido para demonstrar conhecimentos em arquitetura de microsserviços, segurança de dados, persistência com JPA, integração com serviços externos e boas práticas com Spring Boot.

---

## ✅ Funcionalidades

- 📄 Cadastrar nova pauta
- 🔓 Abrir sessão de votação (com tempo determinado ou padrão de 1 minuto)
- 🗳️ Receber votos (Sim/Não) de associados identificados por CPF
- 📊 Contabilizar votos e retornar o resultado da votação

---

## 🧪 Funcionalidade Bônus

### 🧩 Validador de CPF Externo (Mock)

- Endpoint simula uma integração externa:
  - CPF pode ser **inválido** (`404`) ou
  - Pode retornar `ABLE_TO_VOTE` ou `UNABLE_TO_VOTE` aleatoriamente.

---

## 🚀 Tecnologias Utilizadas

<p align="center">
  <img src="https://img.shields.io/badge/Java_21-ED8B00?style=for-the-badge&logo=java&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Oracle_DB-F80000?style=for-the-badge&logo=oracle&logoColor=white" />
  <img src="https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white" />
  <img src="https://img.shields.io/badge/MapStruct-FFB300?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Lombok-4B8BBE?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" />
  <img src="https://img.shields.io/badge/JUnit_5-25A162?style=for-the-badge&logo=junit5&logoColor=white" />
  <img src="https://img.shields.io/badge/Jacoco-EC1C24?style=for-the-badge" />
</p>

- **Java 21** — linguagem principal utilizada no projeto
- **Spring Boot** — criação de API REST com Web, Data JPA, Validation
- **Oracle Database** — banco de dados relacional utilizado com o driver `ojdbc11`
- **Flyway** — controle de versionamento e migração de banco de dados
- **MapStruct** — mapeamento automático entre DTOs e entidades
- **Lombok** — geração automática de getters/setters e construtores
- **Swagger (Springdoc OpenAPI)** — documentação da API
- **JUnit 5 + Mockito** — testes automatizados
- **Jacoco** — cobertura de testes

---

## ✅ Pré-requisitos

Antes de executar o projeto, verifique se você possui instalado:

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Gradle 8+](https://gradle.org/install/) (opcional, o projeto já possui `gradlew`)
- [Docker e Docker Compose](https://docs.docker.com/compose/) (caso deseje executar com banco de dados em container)

---
## 🔗 Relacionamento entre Entidades

A estrutura do banco de dados segue uma modelagem relacional clara e consistente, conforme o diagrama abaixo:

### 🧩 Entidades e Relacionamentos

- **ASSOCIADO**
    - Cada associado é identificado por um `ID_ASSOCIADO`.
    - Um associado pode registrar **múltiplos votos**.

- **PAUTA**
    - Representa um tema a ser votado.
    - Cada pauta pode conter **múltiplas sessões de votação**.

- **SESSAO_VOTACAO**
    - Cada sessão pertence a **uma única pauta**.
    - Uma sessão de votação pode ter **vários votos registrados**.

- **VOTO**
    - Cada voto está vinculado a um **associado** e a uma **sessão de votação**.
    - Um associado pode votar **uma única vez por sessão**, conforme regra de negócio.

### 🔐 Regras de Integridade

- Ao tentar excluir um associado, pauta ou sessão, o banco impede a operação se houver votos relacionados (integridade referencial).
- Todas as chaves estrangeiras são `NOT NULL`, garantindo vínculos obrigatórios entre as entidades.

---
<img width="1181" height="454" alt="Image" src="https://github.com/user-attachments/assets/aece60f1-9f8c-4c85-8dd4-52328ead38b0" />

---

## ▶️ Como Executar o Projeto

### 1. Clone o repositório

```bash
  git clone https://github.com/seu-usuario/desafio-votacao.git
```

1. **Limpeza e compilação do projeto**:
   Execute o seguinte comando para limpar e compilar o projeto:

```bash
 ./gradlew clean build
 ./gradlew bootRun
 ```

### Opção 2: Usando Docker

2. **Subir os containers com Docker Compose**:
   Execute o seguinte comando para construir e subir os containers:

```bash
  docker-compose up -d --build
```
## 📚 Documentação da API

A documentação interativa da API está disponível via Swagger UI, fornecida pelo SpringDoc OpenAPI.

### 🔗 Acesso

Após iniciar a aplicação, acesse:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

> Você poderá visualizar todos os endpoints disponíveis, realizar requisições diretamente pela interface, verificar os contratos dos DTOs e acompanhar os códigos de resposta da API.




