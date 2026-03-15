# Helios Observability

Helios Observability é uma API REST desenvolvida em Java com Spring Boot cujo objetivo é fornecer um sistema simples de monitoramento de serviços, permitindo acompanhar a disponibilidade de hosts atraves de ICMP (Ping).

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
## Execução da Aplicação:

O Helios Observability pode ser executado utilizando Docker e Docker Compose, o que facilita a configuração do ambiente e das dependências como banco de dados e ferramentas de observabilidade.
### Pré-requisitos:
Certifique-se de ter instalado:
- Docker
- Docker Compose
Verifique a instalação:

```
docker --version
docker compose version
```
### Subindo a aplicação

Clone o repositório
```
git clone https://github.com/Neyzim/helios-observability
cd helios-observability
```
Suba todos os containers:
```
cd helios-observability
```
Esse comando irá iniciar:
- Aplicação Helios Observability
- PostgreSQL
- Prometheus
- Grafana

Após a inicialização, a API estará disponível em:
``http://localhost:8080``

Parando os containers:

```docker compose down```

Se quiser remover os volumes, use:

```docker compose down -v```
***
## Documentação da API

A API Helios é documentada automaticamente com OpenApi/Swagger

Após a inicialização, a documentação pode ser acessada em:
```http://localhost:8080/swagger-ui/index.html#/```

Na interface, é possivel:

- Visualizar todos os Endpoints da API
- Ver os Schemas e parâmetros das requisições
- Testar chamadas diretamente pelo navegador

A especificação OpenApi está disponível em:

```http://localhost:8080/v3/api-docs```

Essa especificação pode ser utilizada para integração com outras ferramentas ou geração automática de clientes da API.
***
## Funcionalidades

Atualmente o Helios Observability permite:

- Monitorar serviços via ICMP (Ping)
- Registrar eventos de disponibilidade (UP / DOWN)
- Gerar alertas quando falhas são detectadas
- Resolver alertas automaticamente quando o serviço volta ao estado saudável
- Expor métricas através de Spring Boot Actuator / Prometheus
***
## Como o Sistema de monitoramento Funciona

O Helios Observability realiza verificações periódicas nos serviços registrados para determinar sua disponibilidade.

O fluxo de monitoramento segue as etapas abaixo:
### 1. Registro do Serviço
Cada serviço monitorado contém informações como:
- nome do serviço
- endereço do endpoint
- política de SLA

Essas informações são armazenadas e utilizadas pelo mecanismo de monitoramento.

### 2. Scheduler de Verificação
Um scheduler interno executa verificações periódicas nos serviços cadastrados.
Para cada serviço monitorado, o sistema:
- executa a verificação 
- registra o resultado da verificação

Exemplos:
- ICMP Check (Ping)
- envia um ICMP Echo Request para o host
- aguarda resposta (Echo Reply)
- registra sucesso ou falha da comunicação

### 3. Avaliação do Estado do Serviço
Após a execução do check, o sistema determina o estado do serviço:
- UP → o serviço respondeu corretamente
- DOWN → falha de conexão ou ausência de resposta

Esse estado é utilizado para alimentar o sistema de alertas e as métricas da aplicação.

***
## Arquitetura:

O projeto foi desenvolvido utilizando:

O projeto foi desenvolvido utilizando:

- Domain Driven Design (DDD)
- Clean Architecture

A aplicação separa responsabilidades entre as seguintes camadas:

- Domain – regras de negócio
- Application / Use Cases – lógica de aplicação
- Infrastructure – banco de dados, integrações
- Interfaces – controllers da API

Essa separação permite maior testabilidade, desacoplamento e evolução do sistema.
***
## Tecnologias Utilizadas:
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Spring Boot Actuator
- Prometheus
- Grafana (Para Visualização)
***
## Métricas e Observabilidade

A aplicação expõe métricas via Spring Boot Actuator que podem ser coletadas pelo Prometheus.
Endpoint de métricas:

```/actuator/prometheus```

