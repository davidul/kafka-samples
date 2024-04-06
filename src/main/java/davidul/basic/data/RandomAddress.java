package davidul.basic.data;

import io.vavr.collection.List;
import net.datafaker.Faker;
import net.datafaker.providers.base.Address;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.time.LocalDateTime;

public class RandomAddress {

    public EventAddress address() {
        Faker faker = new Faker();
        final Address address = faker.address();
        return new EventAddress(address.city(),
                address.state(),
                address.streetAddress(),
                address.country(),
                faker.name().firstName(),
                faker.name().lastName());
    }

    public Event getEvent(Integer i) {
        final List<String> eventType = List.of("CREATE", "DELETE", "UPDATE");
        final Event event = new Event();
        event.setCreated(LocalDateTime.now());
        event.setKey(i.toString());
        event.setType(eventType.shuffle().get(0));
        event.setEventAddress(address());
        return event;
    }

    /**
     * Produces a flux of events
     * @param max number of events
     * @param millis
     * @return flux of events
     */
    public Flux<Tuple2<Integer, Event>> produceEvents(int max, int millis) {
        RandomAddress randomAddress = new RandomAddress();
        return Flux.range(1, max)
                .delayElements(Duration.ofMillis(millis))
                .map(integer -> Tuples.of(integer, randomAddress.getEvent(integer)));
    }

    /**
     * Produces a list of events
     * @param max number of events
     * @return list of events
     */
    public List<Event> getEvents(int max) {
        RandomAddress randomAddress = new RandomAddress();
        return List.range(1, max).map(randomAddress::getEvent);
    }
}
