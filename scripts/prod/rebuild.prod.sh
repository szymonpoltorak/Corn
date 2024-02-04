#!/bin/sh

COMPOSE_FILE = "../../docker-compose.prod.yml"

docker compose -f "${COMPOSE_FILE}" down --rmi all --remove-orphans
[ "$?" = 0 ] || exit 1

docker compose -f "${COMPOSE_FILE}" up --build -d
[ "$?" = 0 ] || exit 1
