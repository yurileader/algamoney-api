API Restful com Spring e Angular.

Projeto prático do curos de FullStack da Algaworks
Objetivo:
+ Criar uma API Restful em Java, para efetuar lançamentos de receitas de despesas
+ Criar um cliente com Angular, para consumir os serviços da API

Funcionalidades/Serviços:


Categorias

Buscar todas as categorias cadastradas
Buscar uma categoria, pelo código
Criar uma nova categoria



Pessoas

Buscar todas as categorias cadastradas
Buscar uma pessoa, pelo código
Cadastrar uma nova pessoa
Remover uma pessoa, pelo código
Atualizar os da dados de uma pessoa, pelo código



Lançamentos

Buscar lançamentos
Buscar lançamentos pela descrição ou data de vencimento
Buscar um lançamento pelo código
Criar um novo lançamento
Remover um lançamento, pelo código
Atualizar um lançamento, pelo código



Regras de Segurança: Autorização e Autenticação
+ Usuário: Admin possui autorização para utilizar todos os serviços
+ Usuário: Maria possui autorização para utilizar somente os serviços de pequisa

Tecnologias:

Modelo arquitetural: REST
Linguagem: Java 8, HTML, CSS, JavaScript, TypeScript, SQL, JPQL
Banco de dados: MySQL
Frameworks:Spring, Hibernate, JWT, Oauth2, Flyway, Angular
Controle de Versão: Git

Observação:

Para utilizar está API será necessário possuir uma instância do banco dados Mysql configurado
na maquina local. Para saber as configurações do Banco de Dados
lêia o arquivo de propriedades Application.properties no diretório resource do projeto.
