package Client;

import java.util.ArrayList;

public class Player {
    public final int id;
    public int headx = (Main.main.framesize - 1) / 20;
    public int heady = (Main.main.framesize - 1) / 20;
    private ArrayList<Integer> xPos = new ArrayList<>();
    private ArrayList<Integer> yPos = new ArrayList<>();
    private int length = 2;
    private final int color;

    private final Main main = Main.main;


    public Player(int id) {
        this.id = id;

        switch (id % 6) {
            case 0 -> color = main.color(0, 255, 0);
            case 1 -> color = main.color(255, 0, 0);
            case 2 -> color = main.color(0, 0, 255);
            case 3 -> color = main.color(255, 255, 0);
            case 4 -> color = main.color(0, 255, 255);
            case 5 -> color = main.color(255, 0, 255);
            default -> color = main.color(255, 255, 255);
        }

    }



    public void draw() {


        for (int x = 0; x < main.foodNumber; x++){
            if ((headx == main.foodX[x] && heady == main.foodY[x]))
            {
                main.foodX[x] = (int) (Main.r.nextDouble() * main.framesize/10);
                main.foodY[x] = (int) (Main.r.nextDouble() * main.framesize/10);

                length++;
            }
        }



        main.fill(color);
        for (int x = xPos.size() - 1; xPos.size() - x <= length; x--) {
            if (x > 0) {
                if (headx == xPos.get(x - 1) && heady == yPos.get(x - 1)) length = 2;
                main.square(xPos.get(x) * 10 + 2.5 + (main.width - main.framesize) / 2d, yPos.get(x) * 10 + 2.5 + (main.height - main.framesize) / 2d, 5);
            }
        }
        main.square((headx * 10) + (main.width - main.framesize) / 2d, (heady * 10) + (main.height - main.framesize) / 2d, 10);

    }

    public Player move(String move) {
        switch (move) {
            case "up":

                if (!(heady - 1 < 0)) {
                    heady--;
                }
                break;
            case "down":

                if (!(heady + 1 > main.framesize / 10 - 1)) {

                    heady++;
                }

                break;
            case "left":

                if (!(headx - 1 < 0)) {

                    headx--;
                }
                break;
            case "right":

                if (!(headx + 1 > main.framesize / 10 - 1)) {
                    headx++;
                }
                break;
            default:
                break;
        }

        xPos.add(headx);
        yPos.add(heady);
        return this;
    }
}
