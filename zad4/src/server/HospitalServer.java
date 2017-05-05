package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class HospitalServer {
    private static final Logger logger = Logger.getLogger(HospitalServer.class.getName());

    private int port = 12345;
    private Server server;

    private void start() throws IOException
    {
        server = ServerBuilder.forPort(port)
                .addService(new HospitalImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                HospitalServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final HospitalServer server = new HospitalServer();
        server.start();
        server.blockUntilShutdown();
    }
}
