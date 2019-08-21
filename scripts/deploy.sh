#!/usr/bin/env bash

mvn clean package

echo "Copy files..."

scp -i ~/.ssh/gcloud_autometer \
	target/autometer-0.0.1-SNAPSHOT.jar \
	autometer@35.188.130.231:/home/autometer/

echo "Restart server..."

ssh -i ~/.ssh/gcloud_autometer autometer@35.188.130.231 << EOF

pgrep java | xargs kill -9
nohup java -jar autometer-0.0.1-SNAPSHOT.jar > log.txt &

EOF

echo "Bye"

