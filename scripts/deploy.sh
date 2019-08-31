#!/usr/bin/env bash

mvn clean package

echo "Copy files..."

scp -i ~/.ssh/gcloud_autometer \
	target/autometer-1.0.jar \
	autometer@35.246.106.155:/home/autometer/

echo "Restart server..."

ssh -i ~/.ssh/gcloud_autometer autometer@35.246.106.155 << EOF

pgrep java | xargs kill -9
nohup java -jar autometer-1.0.jar > log.txt &

EOF

echo "Bye"
