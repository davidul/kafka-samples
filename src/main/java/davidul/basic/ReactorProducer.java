package davidul.basic;

import com.google.gson.*;
import davidul.basic.data.Event;
import davidul.basic.data.RandomAddress;
import io.vavr.collection.List;
import org.apache.kafka.clients.producer.ProducerRecord;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;

/**
 * Producing sample events
 */
public class ReactorProducer {


    public List<ProducerRecord<String, String>> eventsAsRecords(int max, String topic) {
        RandomAddress randomAddress = new RandomAddress();
        List<Event> events = randomAddress.getEvents(max);
        Gson gson = Config.gson();
        return events.map(f -> new ProducerRecord<>(topic, f.getKey(), gson.toJson(f)));
    }
}
