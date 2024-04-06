package davidul.basic;

import davidul.basic.data.Event;
import davidul.basic.data.RandomAddress;
import davidul.basic.data.Transformers;
import io.vavr.collection.List;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * Sample producer.
 *
 * @author ulicny.david@gmail.com
 */
public class Producer<K, V> {

    private final KafkaProducer<K, V> kafkaProducer;
    private final static Logger logger = LoggerFactory.getLogger(Producer.class);


    public Producer(String bootstrap) {
        this.kafkaProducer = new KafkaProducer<>(Config.producerProperties(bootstrap));
    }

    /**
     * Send data to Kafka
     *
     * @param records
     */
    public void produce(List<ProducerRecord<K, V>> records) {
        logger.info("Producing records");
        records.forEach(r -> kafkaProducer.send(r,
                (recordMetadata, e) -> {
                    logger.info("Offset {} , partition {}", recordMetadata.offset(), recordMetadata.partition());
                    if (e != null)
                        logger.error("Exception", e);
                }));
        kafkaProducer.flush();
        kafkaProducer.close();
    }

    public static void main(String[] args) {
        Producer<String, String> producer = new Producer<>(Config.BOOTSTRAP);
        RandomAddress randomAddress = new RandomAddress();
        List<Event> events = randomAddress.getEvents(10);
        List<ProducerRecord<String, String>> producerRecords = Transformers.eventsAsRecords(events, "my-topic");
        producer.produce(producerRecords);
    }

}
