# HOW TO — Dimdim Music Library

## 1. Pré-requisitos
- Azure CLI instalado e funcionando (`az version`)
- Java 21 (`java -version`)
- Maven (`mvn -version`)
- Uma assinatura Azure ativa

## 2. Login
```powershell
az login
az account show
```

## 3. Criar infraestrutura
Abra `scripts/azure-deploy.ps1`, altere `$SQL_PASSWORD` para uma senha forte e execute:
```powershell
.\scripts\azure-deploy.ps1
```
Anote os nomes exibidos ao final.

## 4. Criar tabelas
Conecte-se ao banco `DimdimMusic` no Azure SQL usando SQL Server Management Studio, Azure Data Studio ou outro cliente SQL compatível. Execute:
```text
database/01_create_tables.sql
```
Opcionalmente execute `database/02_test_data.sql`.

## 5. Gerar o JAR
Na raiz do projeto:
```powershell
mvn clean package
```
O arquivo esperado é `target/dimdim-music-1.0.0.jar`.

## 6. Deploy pelo Azure CLI
Substitua os nomes pelos valores exibidos pelo script:
```powershell
az webapp deploy --resource-group rg-dimdim-music --name NOME_DO_APP --src-path target/dimdim-music-1.0.0.jar --type jar
```

## 7. Testar
```text
https://NOME_DO_APP.azurewebsites.net/health
https://NOME_DO_APP.azurewebsites.net/swagger
```

## 8. Demonstrar persistência
A avaliação deve mostrar a persistência no banco após as operações. Sugestão:
1. POST álbum.
2. Consultar `SELECT * FROM albuns`.
3. POST música usando o `id` do álbum.
4. Consultar `SELECT * FROM musicas` e o JOIN em `database/03_verification.sql`.
5. GET, PUT e DELETE e repetir a consulta correspondente.

## 9. Application Insights
O script cria o recurso Application Insights e configura `APPLICATIONINSIGHTS_CONNECTION_STRING` no App Service.

## 10. Entrega
O GitHub deve conter o código-fonte, DDL, scripts e este HOW TO. Se for solicitado JSON das operações, os exemplos de request/response podem ser registrados a partir do Swagger.
