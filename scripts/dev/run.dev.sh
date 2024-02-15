#!/bin/sh

COMPOSE_FILE="../../docker-compose.dev.yml"

docker compose -f "${COMPOSE_FILE}" up --build -d
[ "$?" = 0 ] || exit 1
