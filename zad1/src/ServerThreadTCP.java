import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Patryk on 2017-03-14.
 */
public class ServerThreadTCP extends Thread {
    Server server;
    Socket socket;
    int port = -1;
    BufferedReader in;
    PrintWriter out;

    public ServerThreadTCP(Server server, Socket clientSocket) throws IOException {
        this.server = server;
        this.socket = clientSocket;
        this.port = socket.getPort();
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                server.handle(port, in.readLine());
            } catch (IOException e) {
                System.out.println("Cannot read message from client " + port);
                server.deleteClient(this);
                stop();
            }
        }
    }

    public void send(String message) {
        out.println(message);
    }

    public int getPort() {
        return port;
    }

    public InetAddress getAddress() {
        return socket.getInetAddress();
    }
}
