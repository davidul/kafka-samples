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

## Network setup
Kafka is currently using `zookeeper` to synchronize the brokers.
For this reason you have to start at least two docker containers -
Kafka broker and Zookeeper. To make the things even more complicated
Kafka networking is divided into two part - internal and external listeners.
In containerized environments you have to take care of both.

You have to change the `ADVERTISED_LISTENER` to reflect your local
environment.


## Cluster start

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

List running containers with `docker ps` command, the output
will be similar to 
```shell
CONTAINER ID   IMAGE          COMMAND                  CREATED              STATUS              PORTS                                                  NAMES
85d7401fc532   docker_kafka   "/bin/sh -c start-ka…"   About a minute ago   Up About a minute   0.0.0.0:49272->9092/tcp, 0.0.0.0:49271->19092/tcp      docker-kafka-1
5e32cb58c5b7   zookeeper      "/docker-entrypoint.…"   About a minute ago   Up About a minute   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, 8080/tcp   zookeeper
```

Substitute `docker-kafka-1` with you container name/ID.

List topics
```shell
docker exec -it docker-kafka-1 /opt/kafka/bin/kafka-topics.sh \
--list \
--bootstrap-server 192.168.43.232:49272 
```

Create topic `my-topic`
```shell
docker exec -it docker-kafka-1 /opt/kafka/bin/kafka-topics.sh \
--bootstrap-server 192.168.43.232:49272 \
--create \
--topic my-topic \
--partitions 1 \
--replication-factor 1
```

Kafka describe topic
```shell
docker exec -it docker-kafka-1 /opt/kafka/bin/kafka-topics.sh \
--describe \
--topic my-topic \
--bootstrap-server 192.168.43.232:49272
```


## Zookeeper

Zookeeper list broker ids
```
docker exec -it zookeeper zkCli.sh ls /brokers/ids
```
docker exec docker-kafka-1 less /opt/kafka/config/server.properties
