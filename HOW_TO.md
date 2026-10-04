````markdown
# Dimdim Music Library — How To

## 1. Visão geral

O Dimdim Music Library é uma aplicação Web desenvolvida em Java com Spring Boot.

A aplicação disponibiliza uma API REST para gerenciamento de:

- Álbuns
- Músicas

O relacionamento entre as entidades é:

```text
Albuns
   |
   | 1:N
   v
Musicas
````

Cada música pertence a um álbum por meio da chave estrangeira `album_id`.

---

# 2. Tecnologias utilizadas

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Maven
* SQL Server / Azure SQL
* Azure App Service
* Azure Application Insights
* Azure CLI
* GitHub

---

# 3. Pré-requisitos

Instalar:

* Java 21
* Maven
* Azure CLI
* Git

Verificar:

```powershell
java -version
mvn -version
az version
git --version
```

Realizar login na Azure:

```powershell
az login
```

---

# 4. Estrutura do projeto

```text
dimdim-music/
├── database/
│   ├── 01_create_tables.sql
│   ├── 02_test_data.sql
│   └── 03_verification.sql
│
├── scripts/
│   └── azure-deploy.ps1
│
├── src/
│   └── main/
│       ├── java/
│       └── resources/
│
├── pom.xml
├── README.md
└── HOW_TO.md
```

---

# 5. Banco de dados Azure SQL

## 5.1 Recursos utilizados

Resource Group:

```text
rg-dimdim-music
```

Região:

```text
mexicocentral
```

Servidor:

```text
dimdimsql7214.database.windows.net
```

Banco:

```text
DimdimMusicDb
```

Usuário:

```text
dimdimadmin
```

---

# 6. Criar as tabelas

O script de criação está disponível em:

```text
database/01_create_tables.sql
```

Ele cria as tabelas:

```text
albuns
musicas
```

Relacionamento:

```text
musicas.album_id
        |
        v
albuns.id
```

A tabela `musicas` possui uma chave estrangeira para `albuns`.

O script pode ser executado pelo Azure Portal em:

```text
Azure SQL Database
→ Query editor / New Query
```

---

# 7. Verificar as tabelas

Executar:

```sql
SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_NAME IN ('albuns', 'musicas');
```

Também é possível utilizar:

```text
database/03_verification.sql
```

---

# 8. Configuração da aplicação

As credenciais do banco não são armazenadas no código-fonte.

O arquivo:

```text
src/main/resources/application.properties
```

utiliza variáveis de ambiente:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

No Azure App Service foram configuradas:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

---

# 9. Compilar a aplicação

Na raiz do projeto:

```powershell
mvn clean package -DskipTests
```

O arquivo gerado será:

```text
target/dimdim-music-1.0.0.jar
```

---

# 10. Recursos Azure utilizados

## Resource Group

```text
rg-dimdim-music
```

## App Service Plan

```text
plan-dimdim-music
```

## Web App

```text
dimdim-music-565562
```

## Application Insights

```text
dimdim-music-insights
```

## Azure SQL

```text
dimdimsql7214
```

Banco:

```text
DimdimMusicDb
```

---

# 11. Configuração do Web App

O Web App utiliza Java 21.

As configurações do banco são armazenadas como Application Settings:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

A configuração do Application Insights utiliza:

```text
APPLICATIONINSIGHTS_CONNECTION_STRING
```

---

# 12. Deploy utilizando Azure CLI

O processo de deploy está documentado no script:

```text
scripts/azure-deploy.ps1
```

Executar a partir da raiz do projeto:

```powershell
.\scripts\azure-deploy.ps1
```

O script:

1. Realiza login na Azure.
2. Verifica o Resource Group.
3. Verifica o Azure SQL.
4. Verifica o App Service Plan.
5. Verifica o Web App.
6. Verifica o Application Insights.
7. Configura o Application Insights.
8. Gera o JAR com Maven.
9. Publica o JAR no Azure App Service.

---

# 13. Deploy manual do JAR

Também é possível publicar diretamente com:

```powershell
az webapp deploy `
  --resource-group rg-dimdim-music `
  --name dimdim-music-565562 `
  --src-path ".\target\dimdim-music-1.0.0.jar" `
  --type jar
```

---

# 14. Endpoints da API

## Álbuns

### GET

```http
GET /api/albuns
```

Lista todos os álbuns.

### GET por ID

```http
GET /api/albuns/{id}
```

### POST

```http
POST /api/albuns
```

Exemplo:

```json
{
  "titulo": "teste",
  "artista": "teste",
  "anoLancamento": 2022
}
```

### PUT

```http
PUT /api/albuns/{id}
```

### DELETE

```http
DELETE /api/albuns/{id}
```

---

# 15. Músicas

### GET

```http
GET /api/musicas
```

### GET por ID

```http
GET /api/musicas/{id}
```

### GET por álbum

```http
GET /api/musicas/album/{albumId}
```

### POST

```http
POST /api/musicas
```

Exemplo:

```json
{
  "titulo": "Get Lucky",
  "duracaoSegundos": 369,
  "albumId": "ID_REAL_DO_ALBUM"
}
```

### PUT

```http
PUT /api/musicas/{id}
```

Exemplo:

```json
{
  "titulo": "Get Lucky - Remastered",
  "duracaoSegundos": 380
}
```

### DELETE

```http
DELETE /api/musicas/{id}
```

---

# 16. Swagger

Ambiente local:

```text
http://localhost:8080/swagger-ui/index.html
```

Ambiente Azure:

```text
https://dimdim-music-565562.azurewebsites.net/swagger-ui/index.html
```

---

# 17. Health Check

Ambiente local:

```text
http://localhost:8080/health
```

Azure:

```text
https://dimdim-music-565562.azurewebsites.net/health
```

---

# 18. Teste de persistência

As operações devem ser verificadas através da API e do banco de dados.

Operações realizadas:

* POST
* GET
* GET por ID
* PUT
* DELETE

Após as operações, os dados podem ser conferidos no Azure SQL.

Exemplo:

```sql
SELECT * FROM albuns;
SELECT * FROM musicas;
```

O objetivo é demonstrar que as operações realizadas pela API são persistidas no banco Azure SQL.

---

# 19. GitHub

Repositório:

```text
https://github.com/samyrcouto/dimdim-music-devops5
```

O repositório contém:

* Código-fonte Java
* `pom.xml`
* Scripts SQL
* Script Azure CLI
* README
* HOW_TO
* Estrutura completa da aplicação

---

# 20. Resultado esperado

Ao final do processo, a aplicação estará:

* Publicada no Azure App Service;
* Desenvolvida em Java 21 com Spring Boot;
* Conectada ao Azure SQL;
* Utilizando duas tabelas relacionadas por chave estrangeira;
* Disponibilizando operações CRUD;
* Documentada através do Swagger;
* Monitorada através do Application Insights;
* Versionada no GitHub.

```
```

