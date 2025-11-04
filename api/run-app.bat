@echo off
echo Configurando e executando a aplicacao...

REM Carregar variáveis de ambiente
call setup-env.bat

REM Executar aplicação
mvnw.cmd spring-boot:run

