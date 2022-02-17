# Kafka samples
## Docker

This is used mainly for development and experiments,
it is expected to rebuild the image frequently. For this 
reason kafka installation archive is NOT downloaded as a
part of the image creation. You can download it directly 
from here
[Kafka downloads](https://kafka.apache.org/downloads)

Inside the Dockerfile is a COPY instruction, place the Kafka
archive to `docker\kafka` or adjust the Dockerfile itself.

```dockerfile
COPY kafka_2.13-3.0.0.tgz /tmp/
```

Kafka version is provided as build argument, you 
can override it from command line.
Default values
```dockerfile
ARG kafka_version=3.0.0
ARG scala_version=2.13
```

You can create Kafka cluster with docker-compose. It will
create zookeeper and kafka container.
Kafka broker id is random. Should not be in collision.

```
cd docker
docker-compose up --build
```
If you need to start over
```
docker-compose rm
docker-compose up --build
```

If you just start the containers, without rebuilding
zookeeper will remember the previous session
```shell
ERROR Error while creating ephemeral at /brokers/ids/1001, node already exists and owner ...
```

Scaling up with
```shell
docker-compose up --scale kafka=3
```
will create three kafka brokers

## Kafka operations

Substitute `docker-kafka-1` with you container name/ID.

List topics
```shell
docker exec -it docker-kafka-1 /opt/kafka/bin/kafka-topics.sh \
--list \
--bootstrap-server 192.168.43.232:59774 
```

Create topic
```shell
docker exec -it docker-kafka-1 /opt/kafka/bin/kafka-topics.sh \ 
--create \
--topic my-topic \ 
--partitions 1 \
--replication-factor 1 \ 
--bootstrap-server 192.168.43.232:59774
```

Kafka describe topic
```shell
docker exec -it docker-kafka-1 /opt/kafka/bin/kafka-topics.sh \
--describe \
--topic my-topic \
--bootstrap-server 192.168.43.232:59774
```


## Zookeeper

Zookeeper list broker ids
```
docker exec -it zookeeper zkCli.sh ls /brokers/ids
```
docker exec docker-kafka-1 less /opt/kafka/config/server.properties
