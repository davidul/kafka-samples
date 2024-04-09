package davidul.basic;

import davidul.basic.data.Event;
import davidul.basic.data.RandomAddress;
import davidul.basic.data.Transformers;
import io.vavr.collection.List;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executes producer and consumer in separate threads
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new RunnableProducer());
        executorService.submit(new RunnableConsumer());
    }
}


/**
 * Create n events and write them to Kafka
 */
class RunnableProducer implements Runnable {
    @Override
    public void run() {
        RandomAddress randomAddress = new RandomAddress();
        List<Event> events = randomAddress.getEvents(10);
        List<ProducerRecord<String, String>> producerRecords = Transformers.eventsAsRecords(events, "my-topic");
        Producer<String, String> producer = new Producer<>(Config.BOOTSTRAP);
        producer.produce(producerRecords);
    }
}

/**
 * Consume events from Kafka
 */
class RunnableConsumer implements Runnable {
    @Override
    public void run() {
        Consumer<String, String> consumer = new Consumer<>(Config.BOOTSTRAP);
        consumer.consume("my-topic");
    }
}
