
# Documentação da Arquitetura — Sistema de Agendamento de Consultas

## Visão Geral
O sistema foi projetado com base em uma arquitetura de microsserviços, composta por três serviços principais que se comunicam entre si de forma segura e desacoplada. 
O objetivo da solução é gerenciar o fluxo completo de autenticação, criação e notificação de consultas médicas, garantindo escalabilidade, segurança e separação de responsabilidades.

## Estrutura dos Serviços

### 1️⃣ API Gateway — Serviço de Autenticação
**Tecnologias:** Spring Boot, Spring Security, JWT

#### Descrição
O API Gateway atua como ponto de entrada único do sistema, responsável por:
- Receber e rotear as requisições para os demais serviços.
- Realizar o processo de login e autenticação de qualquer tipo de usuário (Paciente, Enfermeiro ou Médico).
- Gerar e validar tokens JWT para controle de acesso aos demais serviços.

#### Autenticação
O login é realizado a partir dos seguintes parâmetros:
- `identificador` → define o tipo de usuário (Paciente, Enfermeiro ou Médico).
- `password` → senha do usuário.

Após a autenticação bem-sucedida, o serviço retorna um token JWT, que contém as informações de identificação e a role (perfil de acesso) do usuário. 
Este token será utilizado nas requisições subsequentes aos outros serviços, garantindo segurança e controle de acesso centralizado.

#### Fluxo do Login
1. O usuário envia uma requisição `POST /login` com `identificador` e `password`.
2. O Gateway valida as credenciais usando Spring Security.
3. É gerado um JWT Token com as informações do usuário (id, role e validade).
4. O token é retornado ao cliente, que deve incluí-lo no header Authorization (`Bearer <token>`) ao acessar outros serviços.

### 2️⃣ Serviço de Consultas — Criação e Persistência
**Tecnologias:** Spring Boot, MySQL, RabbitMQ

#### Descrição
Este serviço é responsável por gerenciar as consultas médicas, realizando a criação e persistência dos dados no banco de dados MySQL.
Além disso, ele atua como um producer (produtor) de mensagens, publicando eventos no RabbitMQ sempre que uma nova consulta é criada.

#### Parâmetros para Criação de Consulta
- `userId` → ID do Paciente.
- `professionalId` → ID do Médico ou Enfermeiro responsável.
- `appointmentDateTime` → Data e hora da consulta.

A requisição para criar a consulta deve conter o token JWT gerado pelo API Gateway no header da requisição, garantindo que apenas usuários autenticados possam registrar consultas.

#### Funcionalidades
- Validação do token JWT recebido no header.
- Persistência das informações no banco de dados MySQL.
- Publicação de uma mensagem no RabbitMQ com os detalhes da consulta criada (para consumo posterior).

#### Exemplo de Mensagem Enviada ao RabbitMQ
```
{
  "userId": 12,
  "professionalId": 5,
  "appointmentDateTime": "2025-10-07T15:00:00",
  "status": "CREATED"
}
```

### 3️⃣ Serviço de Notificação — Envio de E-mails
**Tecnologias:** Spring Boot, RabbitMQ, JavaMail

#### Descrição
O serviço de Notificação atua como consumer (consumidor) do RabbitMQ, escutando a fila de mensagens publicada pelo serviço de Consultas.

Quando uma nova mensagem de criação de consulta é recebida, o serviço:
1. Processa os dados recebidos.
2. Gera e envia um e-mail de notificação para o paciente, informando os detalhes da consulta (data, hora e profissional responsável).

#### Comunicação Assíncrona
A comunicação entre o serviço de Consultas (producer) e o serviço de Notificação (consumer) é assíncrona, o que garante:
- Desacoplamento entre os serviços.
- Maior resiliência do sistema.
- Escalabilidade, permitindo múltiplos consumidores processando mensagens em paralelo.

## Fluxo Geral da Arquitetura
Usuário → API Gateway → Serviço de Consultas → RabbitMQ → Serviço de Notificação (E-mail)

## Benefícios da Arquitetura
- Segurança centralizada via JWT.
- Baixo acoplamento via RabbitMQ.
- Escalabilidade horizontal.
- Resiliência a falhas.
- Separação clara de responsabilidades.

## Tecnologias Utilizadas
| Camada | Tecnologia |
|--------|-------------|
| Gateway | Spring Boot, Spring Security, JWT |
| Persistência | MySQL |
| Mensageria | RabbitMQ |
| Backend geral | Spring Boot |
| Notificações | JavaMail Sender |
