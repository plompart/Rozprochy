import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Patryk on 2017-03-15.
 */
public class ClientThreadUDP extends Thread {
    Client client;
    DatagramSocket socket;
    byte[] receiveBuffer = new byte[1024];
    InetAddress address;

    public ClientThreadUDP(Client client, DatagramSocket socket) {
        this.client = client;
        this.socket = socket;
        try {
            address = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());

        }

        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("UDP message: " + msg);
            } catch (IOException e) {
                System.out.println("Cannot receive message via UDP " + e.getMessage());
                client.close();
            }

        }
    }

    public void send(String message) {
        try {
            byte[] msg = message.getBytes();
            address = InetAddress.getByName("localhost");
            DatagramPacket packet = new DatagramPacket(msg, msg.length, address, 12345);
            socket.send(packet);
        } catch (IOException e) {
            System.out.println("Cannot send a message via UDP " + e.getMessage());
        }

    }
}
