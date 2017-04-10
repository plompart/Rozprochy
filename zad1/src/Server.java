import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patryk on 2017-03-13.
 */
public class Server extends Thread {
    List<ServerThreadTCP> clients = new ArrayList<ServerThreadTCP>();
    ServerSocket serverSocket = null;
    DatagramSocket socketUDP;
    ServerThreadUDP serverUDP;
    int port = 12345;

    public Server() {
        start();
    }

    public static void main(String[] args) {
        new Server();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            socketUDP = new DatagramSocket(port);
            serverUDP = new ServerThreadUDP(this, socketUDP);
        } catch (IOException e) {
            System.out.println("Cannot create server socket for port:" + port + " " + e.getMessage());
            System.exit(-1);
        }

        System.out.println("Connected!");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                ServerThreadTCP client = new ServerThreadTCP(this, clientSocket);

                clients.add(client);
            } catch (IOException e) {
                System.out.println("Cannot accept connection " + e);
            }
        }
    }

    public void handle(int name, String message) {
        for (ServerThreadTCP st : clients) {
            if (st.port != name) st.send(name + "> " + message);
        }
    }

    public void deleteClient(ServerThreadTCP st) {
        clients.remove(st);
    }

    public List<ServerThreadTCP> getConnectedClients() {
        return clients;
    }
}
