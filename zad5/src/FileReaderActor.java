import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static akka.actor.SupervisorStrategy.restart;

/**
 * Created by Patryk on 2017-05-22.
 */

public class FileReaderActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private final File file;

    public FileReaderActor(String filename){
        this.file = new File(filename);
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
            .match(String.class, s -> {
                int value = -1;
                Scanner scanner = new Scanner(new FileInputStream(file));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith(s + " ")) {
                        String price = line.substring(line.indexOf(" ") + 1);
                        value = Integer.parseInt(price);
                        break;
                    }
                }

                getSender().tell(value, getSelf());
            })
            .matchAny(o -> log.info("received unknown message"))
            .build();
    }
}


