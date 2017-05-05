import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * Created by Patryk on 2017-04-12.
 */

public class Technician {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String name;
    private final List<String> specs = new ArrayList<>();
    private Channel logWriter;

    private final String INFO_EXCHANGE_NAME = "info";
    private final String LOG_EXCHANGE_NAME = "log";

    public Technician() throws Exception {
        System.out.println("Enter name");
        name = br.readLine();

        for (int i = 1; i <= 2; i++) {
            String spec = "";
            while (spec.equals("") || !Arrays.asList("ankle", "elbow", "knee").contains(spec)) {
                System.out.println("Enter " + i + " specialization from possible [ankle, elbow, knee]:");
                spec = br.readLine();
            }

            specs.add(spec);
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        //info listener direct exchange
        Channel infoListener = connection.createChannel();
        infoListener.exchangeDeclare(INFO_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        String queueName = infoListener.queueDeclare().getQueue();
        infoListener.queueBind(queueName, INFO_EXCHANGE_NAME, "info");
        infoListener.basicConsume(queueName, true, new DefaultConsumer(infoListener) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received from admin: " + message);
            }
        });

        //log writer topic exchange
        logWriter = connection.createChannel();
        logWriter.exchangeDeclare(LOG_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        //specs listener queues
        specs.forEach((exam) -> {
            String key = "exam." + exam;
            try {
                Channel channel = connection.createChannel();
                channel.basicQos(1);
                channel.queueDeclare(key, false, false, false, null);
                channel.basicConsume(key, false, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String message = new String(body, "UTF-8");
                        System.out.println("Received: " + message);

                        try {
                            Random rand = new Random();
                            int value = rand.nextInt(5) + 1;

                            Thread.sleep(value * 1000);

                            String[] parts = message.split(" ");

                            Channel responseChannel = connection.createChannel();
                            responseChannel.queueDeclare(parts[0], false, false, false, null);

                            channel.basicAck(envelope.getDeliveryTag(), false);
                            String responseMessage = "Examinated " + parts[2] + " for " + parts[1] + " injury by " + name;
                            responseChannel.basicPublish("", parts[0], null, responseMessage.getBytes());

                            responseChannel.close();
                            logWriter.basicPublish(LOG_EXCHANGE_NAME, name, null, responseMessage.getBytes());

                        } catch (TimeoutException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        System.out.println("Waiting for examinations...");
    }


    public static void main(String[] args) throws Exception {
        new Technician();
    }
}
