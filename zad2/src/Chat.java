import pl.edu.agh.dsrg.sr.chat.protos.ChatOperationProtos.ChatAction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Patryk on 2017-03-30.
 */

public class Chat {
    private String MANAGEMENT_CHANNEL = "ChatManagement321321";
    private String nickname;
    private ManagementChannel managementChannel;
    private State state;
    private final Map<String, Channel> channels = new HashMap<>();
    private Channel activeChannel;

    public Chat(String nickname) {
        this.nickname = nickname;
        state = new State();
    }

    public void start() {
        managementChannel = new ManagementChannel(MANAGEMENT_CHANNEL, state, nickname);
    }

    public void connectToChannel(String channelName) throws Exception {
        if (!channels.containsKey(channelName)) {
            channels.put(channelName, new Channel(channelName, nickname));
            try {
                managementChannel.send(
                        ChatAction.newBuilder().setAction(ChatAction.ActionType.JOIN)
                                .setChannel(channelName)
                                .setNickname(nickname).build());
            } catch (Exception e) {
                e.getMessage();
            }
        }

        switchChannel(channelName);
    }

    public void send(String message) throws Exception {
        activeChannel.send(message);
    }


    public void switchChannel(String channelName) {
        if (channels.containsKey(channelName)) {
            activeChannel = channels.get(channelName);
        } else {
            System.out.println("There is no channel " + channelName);
        }
    }

    public void printChannelNames() {
        System.out.println("Connected channels");
        for (String channelName : channels.keySet()) {
            System.out.println("\t" + channelName);
        }
    }

    public void printMembers(String channelName) {
        state.printMembers(channelName);
    }

    public void disconnect(String channelName) {
        if (channels.containsKey(channelName) && !channels.isEmpty()) {
            Channel channel = channels.get(channelName);
            channels.remove(channelName);
            if (activeChannel == channel) {
                activeChannel = channels.get(0);
            }
            channel.close();
            try {
                managementChannel.send(
                        ChatAction.newBuilder().setAction(ChatAction.ActionType.LEAVE)
                                .setChannel(channelName)
                                .setNickname(nickname).build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        for (final String channelName : channels.keySet()) {
            disconnect(channelName);
        }
    }


}
