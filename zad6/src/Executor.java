import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Created by Patryk on 2017-06-07.
 */

public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {
    private DataMonitor dm;
    private ZooKeeper zk;
    private final String znode;
    private String[] exec;
    private Process child;

    public Executor(String hostPort, String znode, String exec[]) throws KeeperException, IOException, InterruptedException {
        this.znode = znode;
        this.exec = exec;
        zk = new ZooKeeper(hostPort, 10000, this);
        dm = new DataMonitor(zk, znode, null, this);

    }

    public static void main(String[] args) {
        if (args.length < 2) {
            for(String str : args){
                System.out.println(str);
            }
            System.err
                    .println("USAGE:hostPort program [args ...]");
            System.exit(2);
        }
        String hostPort = args[0];
        String znode = "/znode_testowy";
        String exec[] = new String[args.length - 1];
        System.arraycopy(args, 1, exec, 0, exec.length);
        try {
            new Executor(hostPort, znode, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        dm.process(event);
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            switch (line) {
                case "tree":
                    try {
                        displayTree(znode, znode, 0);
                    } catch (KeeperException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private void displayTree(String child, String path, int i) throws KeeperException, InterruptedException {
        IntStream.rangeClosed(0, i).forEach(k -> System.out.print(" "));
        List<String> children = zk.getChildren(path, dm);
        System.out.println(child);
        children.forEach(c -> {
            try {
                displayTree(c, path + "/" + c, i + 1);
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void exists(byte[] data) {
        if (data == null) {
            if (child != null) {
                child.destroyForcibly();
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException ignored) {
                }
            }
            child = null;
        } else {
            if (child != null) {
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                zk.getChildren(znode, this);
                child = Runtime.getRuntime().exec(exec);
            } catch (IOException | InterruptedException | KeeperException e) {
                e.printStackTrace();
            }
        }
    }
}
