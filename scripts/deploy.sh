#!/usr/bin/env bash

mvn clean package

echo "Copy files..."

scp -i ~/.ssh/autometer \
	target/autometer-2.0.jar \
	yorix@93.189.45.47:~/autometer/

echo "Restart server..."

ssh -i ~/.ssh/autometer yorix@93.189.45.47 << EOF

pgrep java | xargs kill -9
nohup java -jar ~/autometer/autometer-2.0.jar > ~/autometer/log.txt &

EOF

echo "Bye"
