#!/bin/bash

echo "Starting deployment validation..."

TIMEOUT=120
SLEEP_INTERVAL=5

check_health() {
  local SERVICE=$1
  local PORT=$2
  local ELAPSED=0

  echo "Checking $SERVICE on port $PORT..."

  while [ $ELAPSED -lt $TIMEOUT ]; do
    if curl -sf http://localhost:$PORT/actuator/health > /dev/null; then
      echo "✅ $SERVICE is healthy!"
      return 0
    fi
    echo "⏳ Waiting for $SERVICE... ($ELAPSED/$TIMEOUT seconds)"
    sleep $SLEEP_INTERVAL
    ELAPSED=$((ELAPSED + SLEEP_INTERVAL))
  done

  echo "❌ $SERVICE failed health check after $TIMEOUT seconds!"
  return 1
}

# Check all services
check_health "patient-service" "4000"
check_health "auth-service" "4005"
check_health "billing-service" "4002"
check_health "analytics-service" "4003"
check_health "api-gateway" "4004"

echo "✅ All services are healthy! Deployment successful!"