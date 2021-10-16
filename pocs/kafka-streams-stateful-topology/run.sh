#!/bin/bash

#
# Create data on the topic
#
docker-compose exec kafka sh -c "kafka-console-producer \
  --bootstrap-server kafka:9092 \
  --topic players \
  --property 'parse.key=true' \
  --property 'key.separator=|' < players.json"

docker-compose exec kafka sh -c "kafka-console-producer \
  --bootstrap-server kafka:9092 \
  --topic products \
  --property 'parse.key=true' \
  --property 'key.separator=|' < products.json"

docker-compose exec kafka sh -c "kafka-console-producer \
  --bootstrap-server kafka:9092 \
  --topic score-events < score-events.json"

#
# Run the kafka stream app
#
./mvnw exec:java -Dexec.mainClass="Main" -Dexec.classpathScope=runtime