#!/bin/sh

while true; do
  echo ">> Iniciando API (online por 30s)..."
  node index.js &
  PID=$!

  sleep 30

  echo ">> Simulando falha... (offline por 10s)"
  kill $PID
  sleep 10
done
