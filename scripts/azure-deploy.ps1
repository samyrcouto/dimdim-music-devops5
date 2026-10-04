```powershell
# Deploy do Dimdim Music Library na Azure
# Requisitos: Azure CLI instalado e az login realizado

$RESOURCE_GROUP = "rg-dimdim-music"
$LOCATION = "mexicocentral"

$SQL_SERVER = "dimdimsql7214"
$SQL_DATABASE = "DimdimMusicDb"
$SQL_USERNAME = "dimdimadmin"

$APP_SERVICE_PLAN = "plan-dimdim-music"
$WEB_APP = "dimdim-music-565562"

$APP_INSIGHTS = "dimdim-music-insights"

Write-Host "=== Dimdim Music Library - Deploy Azure ===" -ForegroundColor Cyan

Write-Host "`n1. Login Azure"
az login

Write-Host "`n2. Resource Group"
az group create `
  --name $RESOURCE_GROUP `
  --location $LOCATION

Write-Host "`n3. Azure SQL Server"
az sql server show `
  --name $SQL_SERVER `
  --resource-group $RESOURCE_GROUP `
  --output table

Write-Host "`n4. Azure SQL Database"
az sql db show `
  --resource-group $RESOURCE_GROUP `
  --server $SQL_SERVER `
  --name $SQL_DATABASE `
  --output table

Write-Host "`n5. App Service Plan"
az appservice plan show `
  --name $APP_SERVICE_PLAN `
  --resource-group $RESOURCE_GROUP `
  --output table

Write-Host "`n6. Web App"
az webapp show `
  --name $WEB_APP `
  --resource-group $RESOURCE_GROUP `
  --output table

Write-Host "`n7. Application Insights"
az monitor app-insights component show `
  --app $APP_INSIGHTS `
  --resource-group $RESOURCE_GROUP `
  --output table

Write-Host "`n8. Configurando Application Insights no Web App"

$APP_INSIGHTS_CONNECTION_STRING = az monitor app-insights component show `
  --app $APP_INSIGHTS `
  --resource-group $RESOURCE_GROUP `
  --query connectionString `
  --output tsv

az webapp config appsettings set `
  --resource-group $RESOURCE_GROUP `
  --name $WEB_APP `
  --settings `
    APPLICATIONINSIGHTS_CONNECTION_STRING="$APP_INSIGHTS_CONNECTION_STRING"

Write-Host "`n9. Gerando JAR"
mvn clean package -DskipTests

Write-Host "`n10. Publicando JAR no Azure App Service"

az webapp deploy `
  --resource-group $RESOURCE_GROUP `
  --name $WEB_APP `
  --src-path ".\target\dimdim-music-1.0.0.jar" `
  --type jar

Write-Host "`n=== Deploy concluido ===" -ForegroundColor Green

Write-Host "Aplicacao:"
Write-Host "https://$WEB_APP.azurewebsites.net"

Write-Host "`nSwagger:"
Write-Host "https://$WEB_APP.azurewebsites.net/swagger-ui/index.html"

Write-Host "`nHealth:"
Write-Host "https://$WEB_APP.azurewebsites.net/health"
```

