package davidul.basic;

import com.google.gson.Gson;
import davidul.basic.data.Event;
import davidul.basic.data.RandomAddress;
import io.vavr.collection.List;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class StreamSampleTest {
    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Test
    public void doit(){
        final AdminClient adminClient = KafkaContainerSetup.setup(kafka);
        final NewTopic first_topic = new NewTopic("first_topic", 1, (short) 1);
        final NewTopic second_topic = new NewTopic("second_topic", 1, (short) 1);
        adminClient.createTopics(List.of(first_topic, second_topic).asJava());

        final Gson gson = new Gson();
        final Producer producer = new Producer(kafka.getBootstrapServers());
        final RandomAddress   randomAddress = new RandomAddress();
        final List<Event> events = randomAddress.getEvents(10)
                .appendAll(randomAddress.getEvents(10));
        final List<ProducerRecord<String, String>> map = events.map(f -> new ProducerRecord<>(first_topic.name(), f.getKey(), gson.toJson(f)));
        producer.produce(map);

        //final StreamSample streamSample = new StreamSample();
        final StreamWithStore streamSample = new StreamWithStore();
        streamSample.doit(kafka.getBootstrapServers(), first_topic.name());

        final Consumer consumer = new Consumer(kafka.getBootstrapServers());
        consumer.consume(second_topic.name());
    }
}
