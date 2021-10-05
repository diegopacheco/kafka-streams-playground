
#!/bin/bash

#
# Create data on the topic
#
docker-compose exec kafka sh -c 'echo "Hi" | kafka-console-producer --bootstrap-server localhost:9092 --topic users'
docker-compose exec kafka sh -c 'echo "This is" | kafka-console-producer --bootstrap-server localhost:9092 --topic users'
docker-compose exec kafka sh -c 'echo "Kafka Streams" | kafka-console-producer --bootstrap-server localhost:9092 --topic users'

#
# Run the kafka stream app
#
./mvnw exec:java -Dexec.mainClass="Main" -Dexec.classpathScope=runtime