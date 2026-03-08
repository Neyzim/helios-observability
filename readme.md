# Helios Observability

Helios Observability é uma API REST desenvolvida em Java com Spring Boot cujo objetivo é fornecer um sistema simples de monitoramento de serviços, permitindo acompanhar a disponibilidade de endpoints HTTP e serviços TCP.

A aplicação coleta informações de disponibilidade, gera alertas, e pode evoluir para incidentes, fornecendo uma base para observabilidade de sistemas.
***
## Objetivo do Projeto

O projeto foi criado para explorar conceitos de:
- Observabilidade de sistemas
- Arquitetura limpa (Clean Architecture)
- Domain Driven Design (DDD)
- Monitoramento de serviços
- Alertas e gestão de incidentes

A ideia é simular, em menor escala, funcionalidades encontradas em ferramentas como:
- Prometheus
- Uptime Kuma
- Datadog
- Pingdom
***
# Funcionalidades

Atualmente o Helios Observability permite:

- Monitorar endpoints HTTP
- Monitorar serviços via ICMP
- Registrar eventos de disponibilidade (UP / DOWN)
- Gerar alertas quando falhas são detectadas
- Resolver alertas automaticamente quando o serviço volta ao estado saudável
- Expor métricas através de Spring Boot Actuator / Prometheus
***

# Arquitetura:

O projeto foi desenvolvido utilizando:

- Domain Driven Design (DDD)
- Clean Architecture
- Separando responsabilidades entre:
- Domain – regras de negócio
- Application / Use Cases – lógica de aplicação
- Infrastructure – banco de dados, integrações
- Interfaces – controllers da API

Essa separação permite maior testabilidade, desacoplamento e evolução do sistema.
***
# Tecnologias Utilizadas:
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Spring Boot Actuator
- Prometheus
