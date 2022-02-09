# agendamento-aula-api
Este projeto foi desenvolvido em [Spring Boot](https://spring.io/quickstart) na versão 2.6.3

## Configurações iniciais
Primeiramente, é necessário ter no mínimo a versão 8 do Java
[Versão Windows](https://www.oracle.com/java/technologies/downloads/#java8-windows)
[Versão Linux - baseados em Debian](https://www.oracle.com/java/technologies/downloads/#java8-linux)
[Versão MacOS](https://www.oracle.com/java/technologies/downloads/#java8-mac)

Após a instalação do Java, precisamos configurar o banco de dados, que nesse caso é o [MySQL 8](https://dev.mysql.com/downloads/mysql/). Ao término da configuração do MySQL, crie um schema chamado `agendamento_aula` para que o Spring Boot possa criar as tabela e preencher com as seeds ao iniciar a aplicação.

Ao iniciar a aplicação, consulte o swagger no endereço [http://localhost:8080/swagger-ui/#/](http://localhost:8080/swagger-ui/#/) para ter acesso aos endpoints.

## Dados padrões
Ao rodar a primeira vez a aplicação, alguns dados são semeados no banco:

```
                            Usuarios

id    email                     nome               senha
1     aluno01@gmail.com         Aluno 01           aluno01@123
2     professor01@gmail.com     Professor 01       professor01@123
3     professor02@gmail.com     Professor 02       professor02@123
-     -                         -                  -
20    professor019@gmail.com    Professor 019      professor019@123
21    professor020@gmail.com    Professor 020      professor020@123
```
