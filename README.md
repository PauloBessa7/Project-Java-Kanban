
# Projeto: API REST de Gerenciamento de Tarefas - Kanban ToDo List

## Descrição

Esta aplicação implementa uma API REST para gerenciar um quadro Kanban de tarefas. O sistema permite a criação, edição, movimentação e exclusão de tarefas em três colunas principais:

- **A Fazer (To Do)**
- **Em Progresso (In Progress)**
- **Concluído (Done)**

Cada tarefa pode ser movida entre essas colunas conforme seu progresso, facilitando a organização e o acompanhamento do status de cada tarefa.

## Tecnologias Utilizadas

- **Java** com **Spring Boot**
- **Banco de dados H2** (in-memory)
- **Postman** para testes de API
- **JPA** (Java Persistence API) para mapeamento e persistência de dados
- **Java Web** para manipulação de requisições HTTP

## Funcionalidades

### Requisitos Funcionais

1. **Cadastro de Tarefas**
   - **Título**: obrigatório.
   - **Descrição**: opcional.
   - **Data de criação**: gerada automaticamente.
   - **Status**: "A Fazer", "Em Progresso", "Concluído".
   - **Prioridade**: baixa, média, alta.
   - **Data limite**: opcional (desafio).

2. **Gerenciamento de Tarefas**
   - Criar novas tarefas na coluna "A Fazer".
   - Listar todas as tarefas organizadas por coluna.
   - Mover tarefas entre as colunas do Kanban na ordem **A Fazer → Em Progresso → Concluído**.
   - Editar tarefas (título, descrição, prioridade, etc.).
   - Excluir tarefas.

### Funcionalidades Extras

- **Ordenação de Tarefas**: ordenar tarefas dentro de cada coluna por prioridade.
- **Filtragem de Tarefas**: filtrar tarefas por prioridade e data limite.
- **Relatório**: gerar um relatório que liste as tarefas por coluna e destaque as tarefas atrasadas.

## Regras de Negócio

- Tarefas só podem ser criadas na coluna "A Fazer".
- A tarefa deve seguir a ordem de movimentação entre colunas: **A Fazer → Em Progresso → Concluído**.
- O título é obrigatório, enquanto a descrição e a data limite são opcionais.

## Endpoints da API

### Tarefas

- **POST /tasks**: Cria uma nova tarefa na coluna "A Fazer".
- **GET /tasks**: Lista todas as tarefas organizadas por coluna.
- **PUT /tasks/{id}/move**: Move uma tarefa para a próxima coluna.
- **PUT /tasks/{id}**: Atualiza os dados de uma tarefa (título, descrição, prioridade).
- **DELETE /tasks/{id}**: Exclui uma tarefa.

## Exemplos de Requisições no Postman

### Criar Tarefa

**POST /tasks**
```json
{
    "title": "Comprar suprimentos",
    "description": "Comprar suprimentos para o escritório",
    "dueDate": "2026-10-30",
    "priority": "alta"
}
```

### Atualizar Tarefa

**PUT /tasks/{id}**
```json
{
    "title": "Tarefa 1", // opcional
    "description": "Descrição da tarefa 1", // opcional
    "createDate": "2023-01-21", // opcional
    "dueDate": "2023-12-31", // opcional
    "status": "Concluído", // opcional
    "priority": "média" // opcional
}
```

## Como Executar o Projeto

1. Clone o repositório.
2. Abra o projeto em sua IDE preferida.
3. Execute o aplicativo Spring Boot.
4. Utilize o Postman ou outra ferramenta de testes para fazer requisições à API.

## Observações
- Ao listar as tarefas no endpoint GET /tasks, será retornado um JSON com todas as tarefas, sem organização por status ou prioridade.
- A filtragem por prioridade e status será exibida apenas como um output no terminal do projeto.
