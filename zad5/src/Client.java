/**
 * Created by Patryk on 2017-05-20.
 */

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Client {
    public static void main(String[] args) throws Exception{
        // config
        File configFile = new File("C:\\Users\\Patryk\\Desktop\\Studia\\VI semestr\\Rozprochy\\zad5\\remote_app.conf");
        Config config = ConfigFactory.parseFile(configFile);

        // create actor system & actors
        final ActorSystem system = ActorSystem.create("bookshop", config);
        final ActorRef local = system.actorOf(Props.create(ClientActor.class), "local");

        // interaction
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Commands: 'search [title]', 'order [title]'");
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            local.tell(line, null);
        }

        system.terminate();
    }
}
