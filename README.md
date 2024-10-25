# Vhshop_quarkus_1.0

## 🌟 Descrição
Aplicação de gerenciamento de locadora feita para testar conceitos do quarkus. 

## 🛠️ Estrutura do projeto 
```plaintext
├── Backend/                 
│   ├── app/
│      ├── src/
│         ├── main/
│         ├── test/
|
├── Frontend/
│   ├── css/
│   ├── html/
│   ├── img/
│   ├── js/
|
├── docker-compose.yaml
└── README.md 
```

## 🚀 Como Usar
1. **Instale uma IDE**:
   - Certifique-se de ter uma IDE compatível com Java para rodar o projeto (como IntelliJ IDEA, Eclipse ou VSCODE).

2. **Configure o Ambiente**:
   - **Java 17**: Você precisará ter o Java 17 ou superior instalado em sua máquina.

3. **Configuração das propriedades do projeto**:
   - Crie um arquivo `application.properties` em `Backend/app/src/main/resources` ele deve guardar todas as configurações necessárias da aplicação, banco de dados, perfis, configurações de logs, etc.   
   
   - O modo _dev_ do quarkus permite um overview sobre toda aplicação assim como funciona o `SonarQuebe`, podendo inspecionar toda aplicação.

   - Ele pode ser dividido em perfils, da forma que você melhor preferir. Para maiores informações consulte: https://quarkus.io/guides/config-reference#profiles
      - Para executar o `application.properties` em perfils diferentes basta: `./mvnw quarkus:NOME_PERFIL` ou `./mvnw quarkus:run -Dquarkus.profile:NOME_PROFILE` 

   - Exemplo de configurações importantes para rodar esse projeto: 
        `quarkus.datasource.db-kind=postgresql`
     
        `quarkus.datasource.username=XPTO`
     
        `quarkus.datasource.password=XPTO`
     
        `quarkus.datasource.jdbc.url=jdbc:XPTO`
     
        `quarkus.datasource.jdbc.max-size=16`
     
        `quarkus-jdbc-postgresql=org.postgresql.Driver`

        `quarkus.http.cors=true`
     
        `quarkus.http.cors.origins=http://localhost:8081 #porta onde o frontend está rodando`
     
        `quarkus.http.cors.methods=GET,PUT,POST,DELETE,OPTIONS`
     
        `quarkus.hibernate-orm.database.generation=update`
     
        `quarkus.http.cors.headers=Content-Type`
     
        `quarkus.http.cors=true`
     
        
     - Para mais informações consulte: https://quarkus.io/guides/datasource#jdbc-configuration

4. **Comandos do Projeto**:
   - Antes de rodar o projeto executar o maven clean install, caso tenha o maven na sua máquina, dentro da pasta do projeto execute no terminal: `mvn clean install`. Se não  basta usar o mvnm que esta no projeto `Backend/app`, executando o mesmo comando por `./mvmn clean install`.
     - Esse comando vai baixar as dependências do projeto.
       
   - Rodar um `./mvnw package` ou caso tenha o maven instalado globalmente: `mvn package`, que vai indicar para o quarkus compilar e gerar o `.jar` da aplicação, gerando a pasta necessária para o sucesso da criação do container. 

   - Já para rodar o projeto caso tenha o maven na sua máquina basta: `mvn quarkus:dev`, se não usar o `./mvmn quarkus:dev`.
     - Já esse comando vai executar a aplicação web.

   - Por fim, para rodar o `docker-compose.yaml` precisa ter o docker instalado e rodar o `docker-compose up`.
      - Dois containers vão ser criados, `frontend` e `backend`. O `frontend` é executado na porta `8081` e o `backend` na porta `8080`.
      - Para a construção do servidor web foi usado o https://static-web-server.net/. Já para o backend foi feito usando o próprio quarkus, por meiodo do Dockerfile.jvm
     
