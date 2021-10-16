### Build
```bash
./mvnw clean install 
```
### Run
Terminal 1
```bash
docker-compose up 
```
Terminal 2
```bash
./run.sh 
```
### Query the Service 
```bash
# Get all leaderboard entries, grouped by game (i.e. productId)
curl -s localhost:7000/leaderboard | jq '.'
```
