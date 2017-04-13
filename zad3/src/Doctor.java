import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Created by Patryk on 2017-04-12.
 */

public class Doctor {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String name;
    private Channel infoListener;
    private Channel logWriter;
    private Channel responseListener;
    private Map<String, Channel> examChannels = new HashMap<>();
    private Connection connection;
    private final String LOG_EXCHANGE_NAME = "log";
    private final String INFO_EXCHANGE_NAME = "info";

    public Doctor() throws Exception {
        System.out.println("Enter name");
        name = br.readLine();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();

        //info listener fanout exchange
        infoListener = connection.createChannel();
        infoListener.exchangeDeclare(INFO_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = infoListener.queueDeclare().getQueue();
        infoListener.queueBind(queueName, INFO_EXCHANGE_NAME, "info");
        infoListener.basicConsume(queueName, false, new DefaultConsumer(infoListener) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received from admin: " + message);
            }
        });

        //log writer topic exchange
        logWriter = connection.createChannel();
        logWriter.exchangeDeclare(LOG_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //response listener queue
        responseListener = connection.createChannel();
        responseListener.queueDeclare(name, false, false, false, null);
        responseListener.basicConsume(name, true, new DefaultConsumer(responseListener) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Response from technician: " + message);
            }
        });

        //exams writers queues
        List<String> examinations = Arrays.asList("ankle", "elbow", "knee");
        examinations.forEach((exam) -> {
            try {
                Channel channel = connection.createChannel();
                channel.queueDeclare(exam, false, false, false, null);
                examChannels.put(exam, channel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sendLoop();
    }

    private void sendLoop() {
        System.out.println("Start sending examinations: ");
        while (true) {
            try {
                String line = br.readLine();
                if (line.equals("quit")) {
                    break;
                }

                String[] parts = line.split(" ");
                if (parts.length != 2) {
                    System.out.println("Bad command");
                    System.out.println("Usage: [name of examination] [name of patient]");
                }

                if (examChannels.containsKey(parts[0])) {
                    String message = String.format("%s %s %s", name, parts[0], parts[1]);
                    examChannels.get(parts[0]).basicPublish("", "exam." + parts[0], null, message.getBytes());
                    logWriter.basicPublish(LOG_EXCHANGE_NAME, name, null, message.getBytes());
                } else {
                    System.out.println("Bad command");
                    System.out.println("Usage: [name of examination] [name of patient]");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            infoListener.close();
            logWriter.close();
            responseListener.close();
            examChannels.forEach((key, channel) -> {
                try {
                    channel.close();
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            });

            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new Doctor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
