#!/bin/bash

echo "Deploying to STAGING environment..."

export OWNER=$1

if [ -z "$OWNER" ]; then
  echo "Usage: ./deploy-to-staging.sh <github-owner>"
  exit 1
fi

echo "Pulling latest images..."
docker compose -f docker-compose.staging.yml pull

echo "Stopping existing staging deployment..."
docker compose -f docker-compose.staging.yml down

echo "Starting staging deployment..."
docker compose -f docker-compose.staging.yml up -d

echo "Waiting for services to start..."
sleep 40

echo "Running health checks..."
bash scripts/validate-deployment.sh

if [ $? -eq 0 ]; then
  echo "Staging deployment successful!"
else
  echo "Staging deployment failed! Running rollback..."
  bash scripts/rollback-deployment.sh $OWNER $PREVIOUS_SHA
  exit 1
fi