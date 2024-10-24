# vhshop_quarkus_1.0

## 🌟 Descrição
Aplicação de gerenciamento de locadora feita para testar conceitos do quarkus. 

## 🚀 Como Usar
1. **Instale uma IDE**:
   - Certifique-se de ter uma IDE compatível com Java para rodar o projeto (como IntelliJ IDEA, Eclipse ou VSCODE).

2. **Configure o Ambiente**:
   - **Java 17**: Você precisará ter o Java 17 ou superior instalado em sua máquina.

3. **Configuração do Banco de Dados**:
   - Crie um arquivo `application.properties` em `Backend/main/resources´ com as credenciais do banco de dados.
   - Exemplo: 
        `
        quarkus.datasource.db-kind=A
        quarkus.datasource.username=B
        quarkus.datasource.password=C
        quarkus.datasource.devservices.port=D    
        quarkus-jdbc-postgresql=E
        quarkus.datasource.jdbc.url=jdbc:F        
        quarkus.datasource.jdbc.max-size=G
        ´

4. **Comandos do Maven**:
   - Antes de rodar o projeto executar o maven clean install, caso tenha o maven na sua máquina, dentro da pasta do projeto execute no terminal: `mvn clean install`. Se não  basta usar o mvnm que esta no projeto `Backend/app´, executando o mesmo comando por `./mvmn clean install`.
     - Esse comando vai baixar as dependências do projeto.
       
   - Já para rodar o projeto caso tenha o maven na sua máquina basta: `mvn quarkus:dev`, se não usar o `./mvmn quarkus:dev`.
     - Já esse comando vai executar a aplicação web.
     