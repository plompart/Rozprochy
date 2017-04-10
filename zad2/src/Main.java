import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {

    public static void main(String[] args) {
        String MANAGEMENT_CHANNEL = "ChatManagement321321";
        System.setProperty("java.net.preferIPv4Stack", "true");

        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type your nickname: ");
        String nickname = "client";
        String line;
        try {
            nickname = in.readLine();
        } catch (IOException e) {
            e.getMessage();
        }

        final Chat chat = new Chat(nickname);
        chat.start();

        try {
            chat.connectToChannel(MANAGEMENT_CHANNEL);

            do {
                line = in.readLine();
                String[] words = line.split(" ");
                if (words[0].equals("/connect")) {
                    chat.connectToChannel(words[1]);
                } else if (words[0].equals("/disconnect")) {
                    chat.disconnect(words[1]);
                } else if (words[0].equals("/switch")) {
                    chat.switchChannel(words[1]);
                } else if (words[0].equals("/list")) {
                    chat.printChannelNames();
                } else if (words[0].equals("/members")) {
                    chat.printMembers(words[1]);
                } else if (words[0].equals("/quit")) {
                    chat.stop();
                    break;
                } else {
                    chat.send(line);
                }
            } while (true);
        } catch (IOException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }

    }
}
