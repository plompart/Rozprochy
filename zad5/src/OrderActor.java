import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Semaphore;

/**
 * Created by Patryk on 2017-05-21.
 */

public class OrderActor extends AbstractActor {
    private static Semaphore semaphore = new Semaphore(1);
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private final File file;

    public OrderActor() throws IOException {
        file = new File("orders.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
            .match(String.class, o -> {
                try{
                    semaphore.acquire();
                    String title = o.substring(o.indexOf(" ") + 1);
                    PrintWriter printWriter = new PrintWriter(new FileOutputStream(file, true));
                    printWriter.append(title).append("\n");
                    printWriter.flush();
                    printWriter.close();
                    getSender().tell(true, getSelf());
                }finally{
                    semaphore.release();
                }
            })
            .matchAny(o -> log.info("received unknown message"))
            .build();
    }
}
