#!/usr/bin/env bash

mvn clean package

echo "Copy files..."

scp -i ~/.ssh/gcloud_autometer \
	target/autometer-1.1.jar \
	autometer@35.196.160.86:/home/autometer/

echo "Restart server..."

ssh -i ~/.ssh/gcloud_autometer autometer@35.196.160.86 << EOF

pgrep java | xargs kill -9
nohup java -jar autometer-1.1.jar > log.txt &

EOF

echo "Bye"
