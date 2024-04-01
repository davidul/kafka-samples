package davidul.basic;

import io.vavr.collection.List;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 *
 * Sample producer.
 *
 * @author ulicny.david@gmail.com
 */
public class Producer<K, V> {

    private final KafkaProducer<K, V> kafkaProducer;


    public Producer(String bootstrap){
        this.kafkaProducer = new KafkaProducer<>(Config.producerProperties(bootstrap));
    }

    /**
     * Send data to Kafka
     *
     * @param records
     */
    public void produce(List<ProducerRecord<K, V>> records){
        records.forEach(r -> kafkaProducer.send(r,
                (recordMetadata, e) -> System.out.println("Offset "
                        + recordMetadata.offset()
                        + " , partition "
                        + recordMetadata.partition())));
        kafkaProducer.flush();
        kafkaProducer.close();
    }

    public static void main(String[] args) {
        Producer<String, String> producer = new Producer<>(Config.BOOTSTRAP);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("my-topic", 0,
                "key", "value");
        producer.produce(List.of(producerRecord));
    }

}
