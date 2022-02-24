package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SnakeServer {

    public static List<Player> players = new ArrayList<>();
    public static int seed;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(4444);



            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerThread t = new ServerThread(clientSocket);
                t.setName(String.valueOf(players.size()));
                players.add(new Player(t));
                t.start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void move (String dir){
        for (Player p : players){
            p.move(dir);
        }
    }

}
