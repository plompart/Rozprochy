import com.google.protobuf.InvalidProtocolBufferException;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.stack.ProtocolStack;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import pl.edu.agh.dsrg.sr.chat.protos.ChatOperationProtos.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Patryk on 2017-03-30.
 */

public class ManagementChannel extends ReceiverAdapter {
    private State state;
    private JChannel channel;

    public ManagementChannel(String channelName, State state, String nickname) {
        this.state = state;
        channel = new JChannel(false);
        channel.setName(nickname);
        setStack();
        channel.setReceiver(this);
        try {
            channel.connect(channelName);
            channel.getState(null, 10000);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public synchronized void send(ChatAction chatAction) throws Exception {
        channel.send(new Message(null, null, chatAction.toByteArray()));
    }

    @Override
    public synchronized void receive(Message message) {
        ChatAction action;
        try {
            action = ChatAction.parseFrom(message.getRawBuffer());

            switch (action.getAction()) {
                case JOIN:
                    state.addMember(action.getChannel(), action.getNickname());
                    break;
                case LEAVE:
                    state.removeMember(action.getChannel(), action.getNickname());
                    break;
            }
        } catch (InvalidProtocolBufferException e) {
            e.getMessage();
        }
    }

    @Override
    public synchronized void getState(OutputStream output) throws Exception {
        state.toChatStateMessage().writeTo(output);
    }

    @Override
    public synchronized void setState(InputStream input) throws Exception {
        ChatState chatState = ChatState.parseFrom(input);
        state.fromChatStateMessage(chatState);
    }

    private void setStack() {
        final ProtocolStack stack = new ProtocolStack();
        channel.setProtocolStack(stack);

        try {
            stack.addProtocol(new UDP().setValue("mcast_group_addr", InetAddress.getByName("230.0.0.36")))
                    .addProtocol(new PING())
                    .addProtocol(new MERGE3())
                    .addProtocol(new FD_SOCK())
                    .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                    .addProtocol(new VERIFY_SUSPECT())
                    .addProtocol(new BARRIER())
                    .addProtocol(new NAKACK2())
                    .addProtocol(new UNICAST3())
                    .addProtocol(new STABLE())
                    .addProtocol(new GMS())
                    .addProtocol(new UFC())
                    .addProtocol(new MFC())
                    .addProtocol(new FRAG2())
                    .addProtocol(new STATE_TRANSFER())
                    .addProtocol(new FLUSH());
        } catch (UnknownHostException e) {
            e.getMessage();
        }

        try {
            stack.init();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
