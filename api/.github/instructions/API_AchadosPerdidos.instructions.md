---
applyTo: '**'
---

![Java](https://img.shields.io/badge/Java-21-red?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.6-brightgreen?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)
![MongoDB](https://img.shields.io/badge/MongoDB-7-green?logo=mongodb)
![Docker](https://img.shields.io/badge/Docker-ready-blue?logo=docker)
![License](https://img.shields.io/badge/license-MIT-lightgrey)

# 🧩 Projeto Spring Boot com PostgreSQL, MongoDB e Docker

Este projeto foi desenvolvido utilizando **Java 21** e **Spring Boot 3.3.6**, integrando **PostgreSQL** e **MongoDB** como bancos de dados.  
A arquitetura segue o padrão **em camadas**, priorizando **desacoplamento**, **testabilidade** e **manutenibilidade**.

---

## 🏗️ Arquitetura

A arquitetura em camadas foi adotada para favorecer manutenibilidade, desacoplamento e testabilidade, permitindo a substituição independente de camadas (por exemplo, trocar o repositório JPA por Dapper ou JDBC puro sem impactar a lógica de negócio).


- **Controller:** expõe endpoints REST.
- **Service:** contém a lógica de negócio.
- **Repository:** abstrai a persistência (PostgreSQL e MongoDB).
- **Query:** consultas específicas ao banco.
- **Mapper:** converte entidades ↔ DTOs.

---

## ⚙️ Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|-------------|---------|-----------|
| **Java** | 21 | Linguagem principal do projeto |
| **Spring Boot** | 3.3.6 | Framework para desenvolvimento rápido e modular |
| **Spring Data JPA** | — | Integração ORM com PostgreSQL |
| **Spring Data MongoDB** | — | Integração com banco NoSQL |
| **PostgreSQL** | 16 | Banco de dados relacional |
| **MongoDB** | 7 | Banco de dados NoSQL |
| **JUnit / Mockito** | — | Testes unitários e mocks |

📌 Seções principais:
Visão Geral - Informações básicas do projeto
Arquitetura - Padrão em camadas e estrutura detalhada
Tecnologias - Todas as 30+ tecnologias utilizadas com versões
Estrutura do Projeto - Árvore completa de diretórios
Configuração - Variáveis de ambiente e perfis
Banco de Dados - PostgreSQL e MongoDB
Segurança - JWT, OAuth2, Roles
APIs e Endpoints - 16 controllers documentados
Integrações - AWS S3, Google OAuth2, WebSocket
Records

🎯 Destaques técnicos:
✅ Java 21 com Spring Boot 3.3.6
✅ 16 Controllers REST
✅ 22+ Entidades JPA
✅ 20+ Services de negócio
✅ Banco híbrido: PostgreSQL + MongoDB
✅ Segurança: JWT + OAuth2 Google
✅ Storage: AWS S3
✅ Chat em tempo real: WebSocket
✅ Cache: Caffeine
✅ Documentação: Swagger/OpenAPI
✅ CVEs mitigados com versões atualizadas