package PlayerVsPlayer.net;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Phaser;

import arc.func.Cons;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class NetLobbyConnection implements Runnable {
    Map<String, Cons<String>> methodsServer = new HashMap<>();

    Socket socket = null;
    BufferedWriter writer = null;
    BufferedReader reader = null;
    String uuid;

    private int hbTimeoutSeconds = 29000;
    private Thread hbThread = null;

    private Phaser phaser = null;

    public NetLobbyConnection(String host, Integer port, String uuid) {
        this.phaser = new Phaser(0);

        try {
            this.socket = new Socket(host, port);

            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.uuid = uuid;
        } catch (Exception e) {
            this.closeConnection(false);
            e.printStackTrace();
        }

        this.hbThread = new Thread(() -> {
            while (!this.socket.isClosed() && this.hbThread != null) {
                try {
                    if (this.phaser.getUnarrivedParties() != 0) { // registration
                        this.phaser.awaitAdvance(this.phaser.getPhase());
                    }

                    if (this.hbThread != null) {
                        Thread.sleep(this.hbTimeoutSeconds);
                        this.sendPacket("Heartbeat", "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    this.closeConnection(true);
                    break;
                }

                System.out.println("Thread while done");
            }
        });
        this.hbThread.start();

        System.out.println("Socket initialize, sending message");
        this.sendPacket("uuid", uuid);
    }

    /*
     * sendPacket takes a type and content, creates the mseeage and sends it to the
     * socket
     */
    public void sendPacket(String type, String content) {
        if (socket.isClosed() || this.writer == null) {
            System.out.println("Cant send message, type: " + type);
            return;
        }

        String message = "!type:" + type + "!content:" + content;

        System.out.println("Sending message out from client: " + message);

        try {
            this.writer.write(message);
            this.writer.newLine();
            this.writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            closeConnection(false);
        }
    }

    /*
     * closeConnection closes gracefully or not a specific socket connection
     */
    public void closeConnection(boolean gracefully) {
        this.phaser.register();

        if (gracefully) {
            this.sendPacket("CloseSocket", uuid);
        }

        try {
            if (this.reader != null) {
                System.out.println("reaader close");
                this.reader.close();
                this.reader = null;
            }
            if (this.writer != null) {
                System.out.println("writer close");
                this.writer.close();
                this.writer = null;
            }
            if (this.socket != null) {
                System.out.println("socket close");
                this.socket.close();
            }
            if (this.hbThread != null) {
                System.out.println("thread close");
                this.hbThread = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.phaser.arrive();
    }

    /*
     * addMethodClient adds runnable methods that clients can call through the
     * socket
     */
    public void addMethodServer(String type, Cons<String> method) {
        this.methodsServer.put(type, method);
    }

    /*
     * handleSocketMessage takes a raw message, formats it, looks for the methods to
     * handle it and calls it.
     */
    private void handleSocketMessage(String sm) {
        if (sm == null) {
            return;
        }

        String[] sections = sm.split("(!type:|!content:)");

        System.out.println("Message received: " + sm);

        Cons<String> method = this.methodsServer.get(sections[1]);

        if (method != null) {
            method.get(sections[2]);
        }
    }

    /*
     * Runnable run function, it listens to any incoming messages
     */
    @Override
    public void run() {
        while (!this.socket.isClosed() && this.reader != null) {
            try {
                if (this.phaser.getUnarrivedParties() != 0) {
                    this.phaser.awaitAdvance(this.phaser.getPhase());
                }

                if (this.reader != null) {
                    handleSocketMessage(this.reader.readLine());
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
                this.closeConnection(false);
                break;
            }
        }
    }
}
