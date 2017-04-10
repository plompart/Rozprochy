import pl.edu.agh.dsrg.sr.chat.protos.ChatOperationProtos.*;
import pl.edu.agh.dsrg.sr.chat.protos.ChatOperationProtos.ChatState;
import pl.edu.agh.dsrg.sr.chat.protos.ChatOperationProtos.ChatState.Builder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Patryk on 2017-03-30.
 */

public class State {
    private final Map<String, Set<String>> membersMap;

    public State() {
        this.membersMap = new HashMap<>();
    }

    public void printMembers(String channelName) {
        System.out.println("Connected members to: " + channelName);
        for (String nickname :
                membersMap.get(channelName)) {
            System.out.println("\t" + nickname);
        }
    }

    public synchronized void addMember(String channelName, String nickname) {
        if (!membersMap.containsKey(channelName)) {
            membersMap.put(channelName, new HashSet<String>());
        }
        membersMap.get(channelName).add(nickname);
        System.out.println(nickname + " joined " + channelName);
    }

    public synchronized void removeMember(String channelName, String nickname) {
        membersMap.get(channelName).remove(nickname);
        if (membersMap.get(channelName).isEmpty()) {
            membersMap.remove(channelName);
        }
        System.out.println(nickname + " left " + channelName);
    }

    public synchronized ChatState toChatStateMessage() {
        Builder builder = ChatState.newBuilder();
        for (String channelName : membersMap.keySet()) {
            for (String nickname : membersMap.get(channelName)) {
                builder.addState(
                        ChatAction.newBuilder()
                                .setChannel(channelName)
                                .setNickname(nickname)
                                .setAction(ChatAction.ActionType.JOIN));
            }
        }
        return builder.build();
    }

    public synchronized void fromChatStateMessage(final ChatState chatState) {
        membersMap.clear();
        for (ChatAction chatAction :
                chatState.getStateList()) {
            addMember(chatAction.getChannel(), chatAction.getNickname());
        }
    }
}
