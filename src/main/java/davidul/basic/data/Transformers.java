package davidul.basic.data;

import com.google.gson.Gson;
import davidul.basic.Config;
import io.vavr.collection.List;
import org.apache.kafka.clients.producer.ProducerRecord;

public class Transformers {

    public static List<ProducerRecord<String, String>> eventsAsRecords(List<Event> events, String topic){
        return events
                .map(f -> new ProducerRecord<>(topic, f.getKey(), toJson(f) ));
    }

    public static List<String> toJson(List<Event> events){
        Gson gson = Config.gson();
        return events.map(gson::toJson);
    }

    public static String toJson(Event event){
        Gson gson = Config.gson();
        return gson.toJson(event);

    }
}
