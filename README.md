# Sistema de Cadastro

Este é um sistema de cadastro desenvolvido em Java que utiliza o console para realizar os comandos. 

## Funcionalidades

O sistema oferece as seguintes funcionalidades:

1. Criar um cadastro
2. Editar um usuário usando o CPF
3. Listar os usuários
4. Procurar um usuário usando o CPF
5. Apagar um usuário usando o CPF
6. Desconectar

## Campos para Cadastro

Ao criar um cadastro, serão solicitadas as seguintes informações do usuário:

- Nome completo
- Data de nascimento
- CPF (não pode ser editado)
- RG
- Gênero
- Email
- Endereço
- Número
- CEP
- Bairro
- Cidade
- Estado
- Complemento
- Nome do pai
- Nome da mãe
- Telefone

## Pré-requisitos

Certifique-se de ter os seguintes componentes instalados antes de executar o sistema:

- Java JDK (versão 8 ou superior)
- MySQL (versão 5.7 ou superior)
- IDE de desenvolvimento Java (recomendado: Eclipse, NetBeans, Intellij IDEA)
- Biblioteca Jansi

## Configuração do MySQL

1. Crie um banco de dados chamado `cadastro` no MySQL.
2. Importe o arquivo `database.sql` para criar a tabela no banco de dados.

## Configuração do Projeto

1. Clone este repositório para sua máquina local.
2. Abra o projeto na sua IDE de desenvolvimento Java.
3. No arquivo `src/main/java/com/exemplo/CadastroApp.java`, altere as seguintes linhas de código para se conectar ao seu banco de dados MySQL:

java
private static final String URL = "jdbc:mysql://localhost:3306/cadastro";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";


## Executando o Sistema

1. No diretório do projeto, compile o código-fonte executando o seguinte comando no console:


javac src/main/java/com/exemplo/*.java


2. Em seguida, execute o sistema com o seguinte comando:


java -classpath src/main/java com.exemplo.CadastroApp


3. Ao executar o sistema, um menu será exibido no console com as opções disponíveis. Basta digitar o número correspondente à opção desejada e pressionar Enter.

## Contribuindo

Sinta-se à vontade para abrir um pull request com melhorias, novas funcionalidades ou correções de bugs.

## Licença

Este projeto é livre.
