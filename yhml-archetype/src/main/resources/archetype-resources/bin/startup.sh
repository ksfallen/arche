#!/bin/bash
APP_NAME=yhml-sys

nohup java -jar ${APP_NAME}.jar --spring.profiles.active=dev --server.port=8000 >/dev/null 2>&1 &
echo "start successfully!"
