# Projeto Locadora

## Descrição

Este é um projeto simples em Java que simula um sistema de locadora. O projeto utiliza um banco de dados MySQL para persistir os dados e oferece um menu de console para realizar operações de CRUD (Criar, Ler, Atualizar e Deletar) para clientes, itens e locações.

## Funcionalidades

O sistema oferece as seguintes funcionalidades através de um menu interativo no console:

### Clientes
* **1. Cadastrar Cliente:** Adiciona um novo cliente ao sistema.
* **2. Editar Cliente:** Atualiza as informações de um cliente existente.
* **3. Deletar Cliente:** Remove um cliente e seu histórico de locações (desde que não haja locações ativas).
* **4. Listar Clientes:** Exibe todos os clientes cadastrados.

### Itens
* **5. Cadastrar Item:** Adiciona um novo item (Filme ou Jogo) para locação.
* **6. Editar Item:** Atualiza as informações de um item existente.
* **7. Deletar Item:** Funcionalidade não implementada.
* **8. Listar Itens:** Exibe todos os itens disponíveis e seus tipos (Filme ou Jogo).

### Locações
* **9. Realizar Nova Locação:** Registra a locação de um item para um cliente.
* **10. Registrar Devolução:** Marca uma locação como finalizada e torna o item disponível novamente.
* **11. Listar Locações Ativas:** Mostra todas as locações que ainda não foram devolvidas.

### Sistema
* **0. Sair:** Encerra a aplicação.

## Estrutura do Projeto

O projeto está organizado nos seguintes pacotes:

* `com.example`: Contém a classe `Main` que inicia o programa e controla o menu.
* `locadora.model`: Contém as classes de modelo que representam as entidades do sistema: `Cliente`, `Item`, `Locacao` e `Tipo`.
* `locadora.dao`: Contém a classe `LocadoraDAO`, responsável por toda a comunicação e operações com o banco de dados (CRUD).
* `locadora.util`: Contém a classe `ConexaoMySQL` para gerenciar a conexão com o banco de dados MySQL.

## Modelos de Dados

* **Cliente:** Representa um cliente da locadora, com atributos como `id`, `nome`, `telefone` e `email`.
* **Item:** Representa um item para locação, com atributos como `id`, `titulo`, `ano`, `precoLocacao`, `disponivel` e um `Tipo`.
* **Tipo:** Define o tipo do item, como "Filme" ou "Jogo".
* **Locacao:** Representa o registro de uma locação, associando um `Cliente` a um `Item`, com datas de locação e devolução.

## Tecnologias Utilizadas

* **Java:** Linguagem de programação principal.
* **Maven:** Para gerenciamento de dependências do projeto.
* **MySQL:** Banco de dados utilizado para armazenar os dados. A dependência `mysql-connector-j` é utilizada para a conexão.

## Configuração do Banco de Dados

A conexão com o banco de dados é configurada na classe `ConexaoMySQL`.

* **URL JDBC:** `jdbc:mysql://localhost:3306/mydb`
* **Usuário:** `root`
* **Senha:** `root`

**Nota:** É necessário ter um servidor MySQL rodando localmente com um banco de dados chamado `mydb` e as credenciais acima para que a aplicação funcione corretamente. O sistema verifica a conexão com o banco de dados na inicialização e encerra a execução em caso de falha.
