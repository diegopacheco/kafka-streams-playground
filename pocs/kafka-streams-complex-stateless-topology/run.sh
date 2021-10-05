#!/bin/bash

#
# Create data on the topic
#
docker-compose exec kafka sh -c 'kafka-console-producer --bootstrap-server kafka:9092 --topic tweets < tweets.json'

#
# Run the kafka stream app
#
./mvnw exec:java -Dexec.mainClass="Main" -Dexec.classpathScope=runtime