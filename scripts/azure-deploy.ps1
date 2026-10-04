$ErrorActionPreference = "Stop"

# Execute após: az login
# Altere SQL_PASSWORD antes de executar.
$RESOURCE_GROUP = "rg-dimdim-music"
$LOCATION = "brazilsouth"
$SUFFIX = Get-Random -Minimum 1000 -Maximum 9999
$APP_NAME = "dimdim-music-$SUFFIX"
$PLAN_NAME = "dimdim-music-plan"
$SQL_SERVER = "dimdim-music-sql-$SUFFIX"
$SQL_DB = "DimdimMusic"
$SQL_ADMIN = "dimdimadmin"
$SQL_PASSWORD = "ALTERE_ESTA_SENHA_FORTE"
$INSIGHTS = "dimdim-music-insights-$SUFFIX"

if ($SQL_PASSWORD -eq "ALTERE_ESTA_SENHA_FORTE") { throw "Altere SQL_PASSWORD no script antes de executar." }

az group create --name $RESOURCE_GROUP --location $LOCATION
az sql server create --name $SQL_SERVER --resource-group $RESOURCE_GROUP --location $LOCATION --admin-user $SQL_ADMIN --admin-password $SQL_PASSWORD
az sql db create --resource-group $RESOURCE_GROUP --server $SQL_SERVER --name $SQL_DB --service-objective S0
az sql server firewall-rule create --resource-group $RESOURCE_GROUP --server $SQL_SERVER --name AllowAzureServices --start-ip-address 0.0.0.0 --end-ip-address 0.0.0.0

az monitor app-insights component create --app $INSIGHTS --location $LOCATION --resource-group $RESOURCE_GROUP --application-type web
az appservice plan create --name $PLAN_NAME --resource-group $RESOURCE_GROUP --location $LOCATION --is-linux --sku B1
az webapp create --resource-group $RESOURCE_GROUP --plan $PLAN_NAME --name $APP_NAME --runtime "JAVA:21-java21"

$CONNECTION = "jdbc:sqlserver://$SQL_SERVER.database.windows.net:1433;databaseName=$SQL_DB;encrypt=true;trustServerCertificate=false;loginTimeout=30"
$INSIGHTS_CONNECTION = az monitor app-insights component show --app $INSIGHTS --resource-group $RESOURCE_GROUP --query connectionString -o tsv

az webapp config appsettings set --resource-group $RESOURCE_GROUP --name $APP_NAME --settings DB_URL=$CONNECTION DB_USERNAME=$SQL_ADMIN DB_PASSWORD=$SQL_PASSWORD APPLICATIONINSIGHTS_CONNECTION_STRING=$INSIGHTS_CONNECTION

Write-Host "Recursos criados. Agora execute: mvn clean package"
Write-Host "Depois: az webapp deploy --resource-group $RESOURCE_GROUP --name $APP_NAME --src-path target/dimdim-music-1.0.0.jar --type jar"
Write-Host "URL: https://$APP_NAME.azurewebsites.net"
Write-Host "Swagger: https://$APP_NAME.azurewebsites.net/swagger"
Write-Host "Health: https://$APP_NAME.azurewebsites.net/health"
Write-Host "SQL Server: $SQL_SERVER.database.windows.net"
