package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread implements Runnable{

    private final Socket clientSocket;
    private PrintWriter out = null;
    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run(){

        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            String ready = in.readLine();
            while (!(ready != null && ready.split(";;")[0].equals("connected"))) {
                ready = in.readLine();
            }
            if (SnakeServer.seed == 0) SnakeServer.seed = Integer.valueOf(ready.split(";;")[1]);
            System.out.println("Client connected!");
            out.println(Thread.currentThread().getName() + ";" + SnakeServer.seed);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            try {



                String line = in.readLine();
                if (line != null && (line.equalsIgnoreCase("up") || line.equalsIgnoreCase("right") || line.equalsIgnoreCase("down") || line.equalsIgnoreCase("left"))) {
                    SnakeServer.move(clientSocket.getInetAddress().toString() + ";;" + line + ";" + Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + clientSocket.getInetAddress() + ";;" + line);
                }

            } catch (IOException e) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return;
            }

        }
    }


    public Socket getClientSocket() {
        return clientSocket;
    }

    public PrintWriter getOut() {
        return out;
    }
}
