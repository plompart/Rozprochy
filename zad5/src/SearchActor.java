import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import java.io.FileNotFoundException;

import static akka.actor.SupervisorStrategy.restart;

/**
 * Created by Patryk on 2017-05-21.
 */

public class SearchActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
            .match(String.class, o -> {
                String title = o.substring(o.indexOf(" ") + 1);

                context().child("filereader1").get().forward(title, context());
                context().child("filereader2").get().forward(title, context());
            })
            .matchAny(o -> log.info("received unknown message"))
            .build();
    }

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(FileReaderActor.class,"database1.txt"), "filereader1");
        context().actorOf(Props.create(FileReaderActor.class,"database2.txt"), "filereader2");
    }

    private static SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .match(FileNotFoundException.class, o -> restart())
            .matchAny(o -> restart())
            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
