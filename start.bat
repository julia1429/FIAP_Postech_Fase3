@echo off
setlocal enabledelayedexpansion

:: --- CONFIGURAÇÕES ---
set MAIN_REPO=https://github.com/julia1429/FIAP_Postech_Fase3
set MAIN_BRANCH=feat_auth
set SUB_REPO=https://github.com/LuccaMeurer/postech_fase3_historico_GraphQL

echo 🚀 Clonando repositório principal...
git clone %MAIN_REPO%
if errorlevel 1 (
    echo ❌ Erro ao clonar o repositório principal.
    exit /b 1
)

cd FIAP_Postech_Fase3

echo 🔄 Fazendo checkout na branch %MAIN_BRANCH%...
git checkout %MAIN_BRANCH%
if errorlevel 1 (
    echo ❌ Erro ao fazer checkout na branch %MAIN_BRANCH%.
    exit /b 1
)

echo 📁 Clonando subprojeto (GraphQL)...
git clone %SUB_REPO%
if errorlevel 1 (
    echo ❌ Erro ao clonar o subprojeto.
    exit /b 1
)

echo ⬆️ Voltando para a pasta raiz...
cd ..

echo 🐳 Subindo containers com Docker Compose...
docker compose up -d --build
if errorlevel 1 (
    echo ❌ Erro ao executar docker compose.
    exit /b 1
)

echo ✅ Setup concluído com sucesso!
pause
