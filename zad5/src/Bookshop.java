import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by Patryk on 2017-05-20.
 */

public class Bookshop {
    public static void main(String[] args) throws Exception{
        // config
        File configFile = new File("C:\\Users\\Patryk\\Desktop\\Studia\\VI semestr\\Rozprochy\\zad5\\remote_app2.conf");
        Config config = ConfigFactory.parseFile(configFile);

        // create actor system & actors
        final ActorSystem system = ActorSystem.create("bookshop", config);
        final ActorRef order = system.actorOf(Props.create(OrderActor.class), "order");
        final ActorRef search = system.actorOf(Props.create(SearchActor.class), "search");

        // interaction
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
        }

        system.terminate();
    }
}
