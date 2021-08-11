#!/usr/bin/env bash

mvn clean package

echo "Copy files..."

scp -i ~/.ssh/am \
	target/autometer-2.0.jar \
	yorix@autometer.pp.ua:~/autometer/

echo "Restart server..."

ssh -i ~/.ssh/am yorix@autometer.pp.ua << EOF

pgrep java | xargs kill -9
nohup java -jar ~/autometer/autometer-2.0.jar > ~/app.log &

EOF

echo "Bye"
