package Client;

import processing.core.PApplet;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main extends PApplet {
    public int id = -1;
    public int framesize = 700;
    public int width = 800;
    public int height = 800;
    public boolean start = false;
    public static final Main main = new Main();
    public static int seed = (int) (Math.random() * 10000000d);
    public static Random r = null;
    public static String move = "";

    /*
        public int headx = (framesize-1)/20;
        public int heady = (framesize-1)/20;
        public ArrayList<Integer> xPos = new ArrayList<Integer>();
        public ArrayList<Integer> yPos = new ArrayList<Integer>();
        public int length = 2;
    */

    public static final ArrayList<Player> players = new ArrayList<>();
    public int foodNumber = (int) Math.pow(framesize / 200d + 1, 2);
    public int[] foodX = new int[foodNumber];
    public int[] foodY = new int[foodNumber];
    public int lastKeyCode;
    public int reverse = 1;


    public static void main(String[] passedArgs) {


        ClientConnector.connect(passedArgs);

        while (main.id == -1) ;

        r = new Random(seed);

        players.add(new Player(main.id));


        String[] appletArgs = {"Client.Main"};
        PApplet.runSketch(appletArgs, main);
    }


    public void settings() {
        size(width, height);
    }

    public void setup() {
        frameRate(10);
        background(40);
    }


    public void draw() {


        for (int x = 0; x < foodNumber; x++) {
            if (foodX[x] == 0 && foodY[x] == 0) {
                foodX[x] = (int) (r.nextDouble() * framesize / 10);
                foodY[x] = (int) (r.nextDouble() * framesize / 10);
            }
        }

        if ((keyCode == UP && lastKeyCode == DOWN) || (keyCode == DOWN && lastKeyCode == UP) || (keyCode == LEFT && lastKeyCode == RIGHT) || (keyCode == RIGHT && lastKeyCode == LEFT)) {
            reverse = 0;
        } else reverse = 1;

        switch (keyCode * reverse + lastKeyCode * (1 - reverse)) {
            case UP:

                if (!(players.get(0).heady - 1 < 0)) {
//                    if (!move.equals("up"))
                    ClientConnector.echo("up");
//                    heady--;
//                    newFrame();

//                    move = "up";

                    players.get(0).move("up");

                }
                break;
            case DOWN:

                if (!(players.get(0).heady + 1 > framesize / 10 - 1)) {
//                    if (!move.equals("down"))
                    ClientConnector.echo("down");

//                    heady++;
//                    newFrame();
//                    move = "down";


                    players.get(0).move("down");
                }

                break;
            case LEFT:

                if (!(players.get(0).headx - 1 < 0)) {
//                    if (!move.equals("left"))
                    ClientConnector.echo("left");

//                    headx--;
//                    newFrame();
//                    move = "left";


                    players.get(0).move("left");
                }
                break;
            case RIGHT:

                if (!(players.get(0).headx + 1 > framesize / 10 - 1)) {
//                    if (!move.equals("right"))
                    ClientConnector.echo("right");

//                    headx++;
//                    newFrame();

//                    move = "right";


                    players.get(0).move("right");
                }
                break;
            default:
                break;
        }

        newFrame();

    }


    void newFrame() {
        if (reverse == 1) lastKeyCode = keyCode;


        background(40);
        fill(color(100, 100, 100));
        square((width - framesize) / 2d - 10, (width - framesize) / 2d - 10, framesize + 20);
        fill(color(0, 0, 0));
        square((width - framesize) / 2d, (width - framesize) / 2d, framesize);
        fill(color(255, 255, 255));


        synchronized (players) {
            for (Player p : players) {
                p.draw();
            }
        }

/*
        fill(color(0, 255, 0));
        for (int x = xPos.size()-1; xPos.size()-x <= length; x--)
        {
            if (x > 0) {
                if (headx == xPos.get(x-1) && heady == yPos.get(x-1)) length = 2;
                square(xPos.get(x) * 10+2.5 + (width-framesize)/2d, yPos.get(x) * 10+2.5 + (height-framesize)/2d, 5);
            }
        }
        square((headx * 10) + (width-framesize)/2d, (heady * 10) + (height-framesize)/2d, 10);
        */
        fill(color(255, 200, 100));
        for (int x = 0; x < foodNumber; x++) {
            noStroke();
            circle((foodX[x] * 10) + (width - framesize) / 2d + 5, (foodY[x] * 10) + (height - framesize) / 2d + 5, 11);

            stroke(0);
        }

    }

    public void square(float x, float y, float size) {
        rect(x, y, size, size);
    }

    public void circle(double x, double y, double size) {
        ellipse((float) x, (float) y, (float) size, (float) size);
    }

    public void square(double x, double y, double size) {
        rect((float) x, (float) y, (float) size, (float) size);
    }

    public void move(String line, int id) {
        synchronized (players) {
            for (Player p : players)
                if (p.id == id) {
                    p.move(line);
                    return;
                }
            players.add(new Player(id).move(line));
        }
        System.out.println("Player: " + id + " joined!");
    }
}
