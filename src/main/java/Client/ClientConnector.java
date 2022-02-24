package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientConnector {


    private static Socket socket = null;
    public static String ip = "127.0.0.1";
    public static int port = 4444;
    public static BufferedReader in = null;

    public static void connect(){connect(new String[0]);}

    public static void connect(String[] args){

        if (args.length == 1) port = Integer.valueOf(args[0]);
        if (args.length == 2) {
            ip = args[0];
            port = Integer.valueOf(args[1]);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (socket != null) socket.close();
                    if (in != null) in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }));


        try {
            socketConnect(ip, port);

            in = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
            echo("connected" + ";;" + Main.seed);

            new Thread(new Runnable() {
                @Override
                public void run() {

                    while (true) {

                        String line = null;
                        try {
                            line = in.readLine();
                        } catch (SocketException e) {
                            System.exit(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (Main.main.id == -1) {
                            Main.main.id = Integer.parseInt(line.split(";")[0]);
                            Main.seed = Integer.parseInt(line.split(";")[1]);
                            System.out.println(Main.main.id + ": Connected!");
                            continue;
                        }

                        System.out.println("Recieve: " + line);
                        int id = Integer.parseInt(line.split(";")[1]);
                        line = line.split(";")[0];
                        if (line != null && Main.main.id != id && (line.equalsIgnoreCase("up") || line.equalsIgnoreCase("right") || line.equalsIgnoreCase("down") || line.equalsIgnoreCase("left"))) {
                            Main.main.move(line, id);
                        }

                    }
                }
            }).start();
//            socket.close();
        } catch (IOException e) {
            System.err.println("Error in Thread " + Thread.currentThread().getName() + ": ");
            e.printStackTrace();
        }
    }


    private static void socketConnect(String ip, int port) throws IOException {
        System.out.println("[Connecting to socket...]");
        socket = new Socket(ip, port);
    }

    // writes and receives the full message int the socket (String)
    public static void echo(String message) {
        try {
            // out & in
            PrintWriter out = new PrintWriter(getSocket().getOutputStream(), true);
            // writes str in the socket and read
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // get the socket instance

    private static Socket getSocket() {
        return socket;
    }
}
