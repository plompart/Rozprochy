import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.util.concurrent.Semaphore;

/**
 * Created by Patryk on 2017-05-21.
 */

public class ClientActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private static final String path = "akka.tcp://bookshop@127.0.0.1:3552/user/";

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
            .match(String.class, s -> {
                if(s.startsWith("search ")){
                    getContext().actorSelection(path + "search").tell(s, getSelf());
                }else if(s.startsWith("order ")){
                    getContext().actorSelection(path + "order").tell(s, getSelf());
                }
            })
            .match(Integer.class, i -> {
                if(i != -1){
                    System.out.println("Price: " + i);
                }else{
                    System.out.println("Could not find title in database");
                }
            })
            .match(Boolean.class, b -> System.out.println("Order successfully added"))
            .matchAny(o -> log.info("received unknown message"))
            .build();
    }

}
