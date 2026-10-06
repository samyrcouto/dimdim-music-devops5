# 🚀 How-To: Deploy do Dimdim Music Library na Azure

## 📌 Pré-requisitos

- Java 21
- Maven
- Azure CLI
- Git
- Conta Azure ativa
- PowerShell

Verificar:

```powershell
java -version
mvn -version
az version
git --version
```

Login:

```powershell
az login
```

---

## 🔹 1. Criar Resource Group

```powershell
az group create `
  --name rg-dimdim-music `
  --location mexicocentral
```

---

## 🔹 2. Criar Azure SQL Server

```powershell
az sql server create `
  --name dimdimsql7214 `
  --resource-group rg-dimdim-music `
  --location mexicocentral `
  --admin-user dimdimadmin `
  --admin-password "SUA_SENHA_FORTE" `
  --enable-public-network true
```

---

## 🔹 3. Criar Banco de Dados

```powershell
az sql db create `
  --resource-group rg-dimdim-music `
  --server dimdimsql7214 `
  --name DimdimMusicDb `
  --service-objective S0
```

---

## 🔹 4. Configurar Firewall

```powershell
az sql server firewall-rule create `
  --resource-group rg-dimdim-music `
  --server dimdimsql7214 `
  --name AllowMyIP `
  --start-ip-address "SEU_IP" `
  --end-ip-address "SEU_IP"
```

Para permitir serviços Azure:

```powershell
az sql server firewall-rule create `
  --resource-group rg-dimdim-music `
  --server dimdimsql7214 `
  --name AllowAzureServices `
  --start-ip-address 0.0.0.0 `
  --end-ip-address 0.0.0.0
```

---

## 🔹 5. Criar Tabelas

O banco utiliza duas tabelas relacionadas:

```text
Albuns
   |
   | 1:N
   v
Musicas
```

O script está em:

```text
database/01_create_tables.sql
```

Executar pelo Azure Portal:

```text
Azure SQL Database → Query editor → New Query
```

As tabelas são:

- `albuns`
- `musicas`

A tabela `musicas` possui a FK:

```text
musicas.album_id → albuns.id
```

---

## 🔹 6. Criar App Service Plan

```powershell
az appservice plan create `
  --name plan-dimdim-music `
  --resource-group rg-dimdim-music `
  --location mexicocentral `
  --sku B1 `
  --is-linux
```

---

## 🔹 7. Criar Web App

```powershell
az webapp create `
  --resource-group rg-dimdim-music `
  --plan plan-dimdim-music `
  --name dimdim-music-565562 `
  --runtime "JAVA:21-java21"
```

---

## 🔹 8. Configurar Banco no Web App

A aplicação utiliza as variáveis:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

Configurar:

```powershell
az webapp config appsettings set `
  --resource-group rg-dimdim-music `
  --name dimdim-music-565562 `
  --settings `
  DB_URL="jdbc:sqlserver://dimdimsql7214.database.windows.net:1433;databaseName=DimdimMusicDb;encrypt=true;trustServerCertificate=false;loginTimeout=30;" `
  DB_USERNAME="dimdimadmin" `
  DB_PASSWORD="SUA_SENHA_FORTE"
```

---

## 🔹 9. Configurar Application Insights

Criar:

```powershell
az monitor app-insights component create `
  --app dimdim-music-insights `
  --location mexicocentral `
  --resource-group rg-dimdim-music
```

Obter a Connection String:

```powershell
az monitor app-insights component show `
  --app dimdim-music-insights `
  --resource-group rg-dimdim-music `
  --query connectionString `
  --output tsv
```

Configurar no Web App:

```powershell
az webapp config appsettings set `
  --resource-group rg-dimdim-music `
  --name dimdim-music-565562 `
  --settings APPLICATIONINSIGHTS_CONNECTION_STRING="CONNECTION_STRING"
```

---

## 🔹 10. Compilar a aplicação

Na raiz do projeto:

```powershell
mvn clean package -DskipTests
```

Será gerado:

```text
target/dimdim-music-1.0.0.jar
```

---

## 🔹 11. Publicar na Azure

```powershell
az webapp deploy `
  --resource-group rg-dimdim-music `
  --name dimdim-music-565562 `
  --src-path ".\target\dimdim-music-1.0.0.jar" `
  --type jar
```

---

## 🔹 12. Testar a API

### Swagger

```text
https://dimdim-music-565562.azurewebsites.net/swagger-ui/index.html
```

### Health Check

```text
https://dimdim-music-565562.azurewebsites.net/health
```

---

## 🔹 13. Endpoints

### Álbuns

```text
GET    /api/albuns
GET    /api/albuns/{id}
POST   /api/albuns
PUT    /api/albuns/{id}
DELETE /api/albuns/{id}
```

### Músicas

```text
GET    /api/musicas
GET    /api/musicas/{id}
GET    /api/musicas/album/{albumId}
POST   /api/musicas
PUT    /api/musicas/{id}
DELETE /api/musicas/{id}
```

Exemplo de criação de álbum:

```json
{
  "titulo": "teste",
  "artista": "teste",
  "anoLancamento": 2022
}
```

Exemplo de criação de música:

```json
{
  "titulo": "Get Lucky",
  "duracaoSegundos": 369,
  "albumId": "ID_REAL_DO_ALBUM"
}
```

---

## 🔹 14. Validar Persistência

Verificar os dados no Azure SQL:

```sql
SELECT * FROM albuns;
SELECT * FROM musicas;
```

Realizar as operações:

- POST
- GET
- PUT
- DELETE

E verificar a persistência dos dados no banco após as operações.

---

## 🔹 15. Evidências

As evidências dos testes estão em:

```text
docs/evidencias/
```

---

## 🔹 16. GitHub

Repositório:

```text
https://github.com/samyrcouto/dimdim-music-devops5
```

---

## ✅ Resultado

Após os passos, a aplicação estará:

- Publicada no Azure App Service;
- Conectada ao Azure SQL;
- Com duas tabelas relacionadas por FK;
- Com CRUD de álbuns e músicas;
- Documentada pelo Swagger;
- Monitorada pelo Application Insights;
- Versionada no GitHub.
