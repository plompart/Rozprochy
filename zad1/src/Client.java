import java.io.*;
import java.net.*;

/**
 * Created by Patryk on 2017-03-13.
 */
public class Client extends Thread {
    Socket socket;
    DatagramSocket socketUDP;
    MulticastSocket multicastSocket;
    BufferedReader in;
    PrintWriter out;
    ClientThreadTCP clientTCP;
    ClientThreadUDP clientUDP;
    ClientThreadUDP clientMulticast;
    DatagramPacket art;
    byte[] asciiMessage = "This is ASCII art".getBytes();

    public Client() {
        start();
    }

    public static void main(String[] args) {
        new Client();
    }

    @Override
    public void run() {
        try {
            socket = new Socket("localhost", 12345);
            System.out.println("Connected as " + socket.getLocalPort());
            InetAddress group = InetAddress.getByName("228.5.6.7");
            multicastSocket = new MulticastSocket(54321);
            multicastSocket.joinGroup(group);
            clientMulticast = new ClientThreadUDP(this, multicastSocket);
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            clientTCP = new ClientThreadTCP(this, new BufferedReader(new InputStreamReader(socket.getInputStream())), out);
            socketUDP = new DatagramSocket(socket.getLocalPort());
            clientUDP = new ClientThreadUDP(this, socketUDP);
        } catch (IOException e) {
            System.out.println("Cannot connect to server " + e.getMessage());
            stop();
        }


        while (true) {
            try {
                String message = in.readLine();
                if (message.equals("M")) {
                    clientUDP.send(message);
                } else if (message.equals("N")) {
                    InetAddress group = InetAddress.getByName("228.5.6.7");
                    art = new DatagramPacket(asciiMessage, asciiMessage.length, group, 54321);
                    multicastSocket.send(art);
                } else {
                    clientTCP.send(message);
                }
            } catch (IOException e) {
                System.out.println("Cannot read message from console" + e.getMessage());
                close();
            }
        }
    }

    public void close() {
        try {
            if (socket != null) socket.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.out.println("Cannot close data streams " + e.getMessage());
        }
        stop();
    }
}
