import java.io.IOException;
import java.net.*;
import java.util.List;

/**
 * Created by Patryk on 2017-03-15.
 */
public class ServerThreadUDP extends Thread {
    Server server;
    byte[] receiveBuffer = new byte[1];
    byte[] asciiMessage = "This is ASCII art".getBytes();
    DatagramPacket art;
    DatagramPacket receivePacket;
    DatagramSocket socketUDP;

    public ServerThreadUDP(Server server, DatagramSocket socketUDP) {
        this.server = server;
        this.socketUDP = socketUDP;
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socketUDP.receive(receivePacket);
                String message = new String(receivePacket.getData());
                handleMessage(message);
            } catch (IOException e) {
                System.out.println("Cannot receive message from UDP canal");
            }

        }
    }

    public void handleMessage(String message) {
        try {
            if (message.equals("M")) {
                List<ServerThreadTCP> clients = server.getConnectedClients();
                for (ServerThreadTCP st : clients) {
                    if (st.getPort() != receivePacket.getPort()) {
                        art = new DatagramPacket(asciiMessage, asciiMessage.length, st.getAddress(), st.getPort());
                        socketUDP.send(art);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot send a UDP message");
        }
    }
}
