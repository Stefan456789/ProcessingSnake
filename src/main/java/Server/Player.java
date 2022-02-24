package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Player{
    ServerThread client;

    public Player(ServerThread client ) {
        this.client = client;
    }

    public void move(String dir){

//        if (dir.split(";;")[0].equals(socket.getInetAddress().toString())) return;

        client.getOut().println(dir.split(";;")[1]);
    }
}
