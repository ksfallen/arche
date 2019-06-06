#!/bin/bash
nohup java -jar saga-sys.jar --spring.profiles.active=dev --server.port=8000 >/dev/null 2>&1 &
echo "start successfully!"