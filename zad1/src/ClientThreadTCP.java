import java.io.*;
import java.net.Socket;

/**
 * Created by Patryk on 2017-03-15.
 */
public class ClientThreadTCP extends Thread {
    Client client;
    BufferedReader in;
    PrintWriter out;

    public ClientThreadTCP(Client client, BufferedReader in, PrintWriter out) {
        this.client = client;
        this.in = in;
        this.out = out;
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(in.readLine());
            } catch (IOException e) {
                System.out.println("Cannot read message from server " + e.getMessage());
                client.close();
                stop();
            }
        }
    }

    public void send(String message) {
        out.println(message);
    }
}
