
up:
	$(MAKE) -C kafka-env up

down:
	$(MAKE) -C kafka-env down

cleanup:
	$(MAKE) -C kafka-env cleanup

download-kafka:
	$(MAKE) -C kafka-env download-kafka