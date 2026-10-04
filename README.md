# Dimdim Music Library

API REST em Java/Spring Boot para gerenciamento de um catálogo musical. O projeto foi criado para o 2º Checkpoint de DevOps Tools & Cloud Computing.

## Tecnologias
- Java 21
- Spring Boot 4.1.1
- Maven
- Spring Web / Spring Data JPA
- SQL Server / Azure SQL Database
- Azure App Service
- Azure CLI
- Application Insights
- Swagger/OpenAPI

## Modelo
`albuns` é a tabela master e `musicas` é a tabela detail. Cada música pertence a um álbum por meio da FK `musicas.album_id -> albuns.id`.

## Executar
Configure `DB_URL`, `DB_USERNAME` e `DB_PASSWORD` como variáveis de ambiente apontando para um SQL Server. Execute o DDL em `database/01_create_tables.sql` e depois:

```powershell
mvn clean package
java -jar target/dimdim-music-1.0.0.jar
```

Swagger: http://localhost:8080/swagger
Health: http://localhost:8080/health

## Endpoints
### Álbuns
GET/POST `/api/albuns`
GET/PUT/DELETE `/api/albuns/{id}`

POST/PUT JSON:
```json
{"titulo":"Kind of Blue","artista":"Miles Davis","anoLancamento":1959}
```

### Músicas
GET/POST `/api/musicas`
GET/PUT/DELETE `/api/musicas/{id}`
GET `/api/musicas?albumId={id}`

POST/PUT JSON:
```json
{"titulo":"So What","duracaoSegundos":545,"albumId":"UUID_DO_ALBUM"}
```

## Azure
Consulte `HOW_TO.md`. O script `scripts/azure-deploy.ps1` cria Resource Group, Azure SQL, App Service, Application Insights e configura as variáveis da aplicação. O deploy do JAR é feito pelo Azure CLI.
