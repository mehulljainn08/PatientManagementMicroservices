#!/bin/bash

echo "Deploying to PRODUCTION environment..."

export OWNER=$1
export VERSION=$2

if [ -z "$OWNER" ] || [ -z "$VERSION" ]; then
  echo "Usage: ./deploy-to-production.sh <github-owner> <version>"
  exit 1
fi

echo "WARNING: You are deploying to PRODUCTION!"
echo "Version: $VERSION"
read -p "Are you sure? (yes/no): " CONFIRM

if [ "$CONFIRM" != "yes" ]; then
  echo "Deployment cancelled."
  exit 0
fi

echo "Pulling version $VERSION images..."
docker compose -f docker-compose.production.yml pull

echo "Stopping existing production deployment..."
docker compose -f docker-compose.production.yml down

echo "Starting production deployment..."
docker compose -f docker-compose.production.yml up -d

echo "Waiting for services to start..."
sleep 40

echo "Running health checks..."
bash scripts/validate-deployment.sh

if [ $? -eq 0 ]; then
  echo "Production deployment successful!"
else
  echo "Production deployment failed! Running rollback..."
  bash scripts/rollback-deployment.sh $OWNER $PREVIOUS_SHA
  exit 1
fi