import com.rabbitmq.client.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

/**
 * Created by Patryk on 2017-04-12.
 */

public class Admin {
    private final String LOG_EXCHANGE_NAME = "log";
    private final String INFO_EXCHANGE_NAME = "info";
    private Channel logListener;
    private Channel infoWriter;
    private Connection connection;

    public Admin() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();

        //log listener exchange topic
        logListener = connection.createChannel();
        logListener.exchangeDeclare(LOG_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = logListener.queueDeclare().getQueue();
        logListener.queueBind(queueName, LOG_EXCHANGE_NAME, "#");
        logListener.basicConsume(queueName, true, new DefaultConsumer(logListener) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("LOG: " + message);
            }
        });

        //info writer exchange direct
        infoWriter = connection.createChannel();
        infoWriter.exchangeDeclare(INFO_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        sendLoop();
    }

    private void sendLoop() {
        System.out.println("Start sending information: ");
        while (true) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                String line = br.readLine();
                if (line.equals("quit")) {
                    break;
                }

                infoWriter.basicPublish(INFO_EXCHANGE_NAME, "info", null, line.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            logListener.close();
            infoWriter.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws Exception {
        new Admin();
    }
}
