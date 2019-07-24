#!/bin/bash
nohup java -Dserver.port=7000 -Dcsp.sentinel.dashboard.server=localhost:7000 -Dproject.name=sentinel-dashboard\
     -Dsentinel.dashboard.auth.username=sentinel\
	 -Dsentinel.dashboard.auth.password=sentinel\
	 -jar sentinel-dashboard.jar >/dev/null 2>&1 &

echo "start successfully!"
