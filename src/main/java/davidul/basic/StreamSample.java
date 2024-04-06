package davidul.basic;

import com.google.gson.Gson;
import davidul.basic.data.Event;
import davidul.basic.data.RandomAddress;
import davidul.basic.data.Transformers;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.state.Stores;

public class StreamSample {

    public static void main(String[] args) {
        StreamSample streamSample = new StreamSample();
        streamSample.doit(Config.BOOTSTRAP, "gson-topic");
    }

    public void doit(String bootstrap, String topic){
        Gson gson = Config.gson();

        RandomAddress reactorProducer = new RandomAddress();
        Producer<String, String> stringEventProducer = new Producer<>(Config.BOOTSTRAP);
        stringEventProducer.produce(Transformers.eventsAsRecords(reactorProducer.getEvents(1), topic));

        final StreamsBuilder streamsBuilder = new StreamsBuilder();
        final KStream<String, String> stream = streamsBuilder.stream(topic, Consumed.with(Serdes.String(), Serdes.String()));
        stream
                .foreach((s1, s2) -> System.out.println("Foreach: Key = " + s1 + " Value: " + s2));

        final KStream<String, String> created = stream
                .filter((k, v) -> gson.fromJson(v, Event.class).getType().equalsIgnoreCase("CREATE"));
        created.to(topic);
        final KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), Config.streamProperties(bootstrap));
        kafkaStreams.start();

        Stores.inMemoryKeyValueStore("aaa");
        //kafkaStreams.store()
        //QueryableStoreType
    }
}
