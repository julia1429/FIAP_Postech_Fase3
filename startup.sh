#!/bin/bash
set -e

# --- CONFIGURAÇÕES ---
MAIN_REPO="https://github.com/julia1429/FIAP_Postech_Fase3"
MAIN_BRANCH="master"
SUB_REPO="https://github.com/LuccaMeurer/postech_fase3_historico_GraphQL"

echo "🚀 Clonando repositório principal..."
git clone "$MAIN_REPO"

cd FIAP_Postech_Fase3

echo "🔄 Fazendo checkout na branch $MAIN_BRANCH..."
git checkout "$MAIN_BRANCH"

echo "📁 Clonando subprojeto (GraphQL)..."
git clone "$SUB_REPO"

echo "🐳 Subindo containers com Docker Compose (banco e broker primeiro)..."
docker compose up -d mysql rabbitmq

echo "⏳ Aguardando o MySQL inicializar..."
# Tenta até 30 vezes (1 segundo por tentativa) verificar se o MySQL está pronto
for i in {1..30}; do
  if docker exec mysql-db mysqladmin ping -h "localhost" --silent &> /dev/null; then
    echo "✅ MySQL está pronto!"
    break
  fi
  echo "⌛ Aguardando MySQL... tentativa $i/30"
  sleep 2
done

# Se o banco não estiver pronto após 30 tentativas, aborta
if ! docker exec mysql-db mysqladmin ping -h "localhost" --silent &> /dev/null; then
  echo "❌ MySQL não ficou pronto a tempo. Abortando setup."
  exit 1
fi

echo "🚀 Subindo todos os outros containers..."
docker compose up -d --build

echo "✅ Setup concluído com sucesso!"

