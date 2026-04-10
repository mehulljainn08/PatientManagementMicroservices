#!/bin/bash

echo "🔄 Starting rollback process..."

REGISTRY="ghcr.io"
OWNER=$1
PREVIOUS_SHA=$2

if [ -z "$OWNER" ] || [ -z "$PREVIOUS_SHA" ]; then
  echo "❌ Usage: ./rollback-deployment.sh <owner> <previous-sha>"
  exit 1
fi

SERVICES=("patient-service" "billing-service" "auth-service" "analytics-service" "api-gateway")

echo "📦 Pulling previous images with SHA: $PREVIOUS_SHA"

for SERVICE in "${SERVICES[@]}"; do
  echo "Pulling $REGISTRY/$OWNER/$SERVICE:$PREVIOUS_SHA..."
  docker pull $REGISTRY/$OWNER/$SERVICE:$PREVIOUS_SHA || echo "⚠️ Could not pull $SERVICE:$PREVIOUS_SHA"
done

echo "🛑 Stopping current deployment..."
docker compose down

echo "🚀 Deploying previous version..."
for SERVICE in "${SERVICES[@]}"; do
  VERSION=$(grep -m 1 "<version>" $SERVICE/pom.xml | sed 's/.*<version>\(.*\)<\/version>.*/\1/')
  VERSION=${VERSION%-SNAPSHOT}
  docker tag $REGISTRY/$OWNER/$SERVICE:$PREVIOUS_SHA $SERVICE:latest || echo "⚠️ Could not tag $SERVICE"
done

docker compose up -d

echo "⏳ Waiting for services to start..."
sleep 30

echo "🏥 Running health checks on rolled back deployment..."
bash scripts/validate-deployment.sh

if [ $? -eq 0 ]; then
  echo "✅ Rollback successful! All services are healthy."
else
  echo "❌ Rollback failed! Manual intervention required."
  exit 1
fi