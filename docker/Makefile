include .env

build-kafka: download-kafka
	docker build -t my-kafka:latest --build-arg="KAFKA_VERSION=$(KAFKA_VERSION)" --build-arg="SCALA_VERSION=$(SCALA_VERSION)" -f kafka/Dockerfile kafka

download-kafka:
	./download-kafka.sh

up: build-kafka
	docker-compose up

cleanup:
	docker-compose rm --force --stop