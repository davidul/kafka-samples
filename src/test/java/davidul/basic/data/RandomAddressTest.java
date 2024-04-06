package davidul.basic.data;

import junit.framework.TestCase;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

public class RandomAddressTest extends TestCase {

    public void testProduceEvents() {
        RandomAddress randomAddress = new RandomAddress();
        Flux<Tuple2<Integer, Event>> tuple2Flux = randomAddress.produceEvents(5, 100);
        tuple2Flux.subscribe(tuple2 -> {
            Event event = tuple2.getT2();
            assertNotNull(event);
            assertNotNull(event.getCreated());
            assertNotNull(event.getKey());
            assertNotNull(event.getType());
            assertNotNull(event.getEventAddress());
            assertNotNull(event.getEventAddress().getCity());
            assertNotNull(event.getEventAddress().getCountry());
            assertNotNull(event.getEventAddress().getFirstName());
            assertNotNull(event.getEventAddress().getLastName());
            assertNotNull(event.getEventAddress().getState());
            assertNotNull(event.getEventAddress().getStreetAddress());
        });
    }

    public void testAddress() {
    }

    public void testGetEvent() {
    }

    public void testGetEvents() {
        RandomAddress randomAddress = new RandomAddress();
        randomAddress.getEvents(5).forEach(event -> {
            assertNotNull(event);
            assertNotNull(event.getCreated());
            assertNotNull(event.getKey());
            assertNotNull(event.getType());
            assertNotNull(event.getEventAddress());
            assertNotNull(event.getEventAddress().getCity());
            assertNotNull(event.getEventAddress().getCountry());
            assertNotNull(event.getEventAddress().getFirstName());
            assertNotNull(event.getEventAddress().getLastName());
            assertNotNull(event.getEventAddress().getState());
            assertNotNull(event.getEventAddress().getStreetAddress());
        });
    }
}