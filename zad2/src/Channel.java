import com.google.protobuf.InvalidProtocolBufferException;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;
import pl.edu.agh.dsrg.sr.chat.protos.ChatOperationProtos.ChatMessage;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Patryk on 2017-03-30.
 */

public class Channel extends ReceiverAdapter {
    private String nickname;
    private String channelName;
    private JChannel channel;
    private InetAddress address;

    public Channel(String channelName, String nickname) throws Exception {
        this.channelName = channelName;
        this.nickname = nickname;

        address = InetAddress.getByName(channelName);

        if (!address.isMulticastAddress()) {
            throw new Exception(address + "is not a multicast address!");
        }

        channel = new JChannel(false);
        setStack();

        try {
            channel.setName(nickname);
            channel.connect(channelName);
            channel.setReceiver(this);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public synchronized void send(String message) throws Exception {
        channel.send(new Message(null, null, ChatMessage.newBuilder()
                .setMessage(message)
                .build()
                .toByteArray()));
    }

    @Override
    public void receive(final Message message) {
        if (channel.getName(message.getSrc()) != nickname) {
            try {
                System.out.println(channel.getName(message.getSrc()) + "@"
                        + channelName + ": " + ChatMessage.parseFrom(message.getRawBuffer()));
            } catch (final InvalidProtocolBufferException e) {
                e.getMessage();
            }
        }
    }

    public synchronized void close() {
        channel.close();
    }

    private void setStack() {
        final ProtocolStack stack = new ProtocolStack();
        channel.setProtocolStack(stack);

        stack.addProtocol(new UDP().setValue("mcast_group_addr", address))
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

        try {
            stack.init();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
