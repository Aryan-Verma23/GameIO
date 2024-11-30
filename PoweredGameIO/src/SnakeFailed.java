  import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
public class SnakeFailed {
    static final int WIDTH = 20;
    static final int HEIGHT = 20;
    static final char WALL = '|';
    static final char SNAKE_HEAD = '^';
    static final char SNAKE_BODY = '*';
    static final char FRUIT = '.';
    static final char EMPTY_SPACE = ' ';

    static int x, y, fruitX, fruitY, score;
    static int[] tailX = new int[100];
    static int[] tailY = new int[100];
    static int nTail;
    static String direction = "STOP";
    static boolean gameOver;

    public static void main(String[] args) throws Exception {
        setup();
        Thread inputThread = new Thread(SnakeFailed::input);
        inputThread.setDaemon(true);
        inputThread.start();

        while (!gameOver) {
            draw();
            logic();
            try {
                Thread.sleep(100); // Slow down game loop
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Game Over! Final Score: " + score);
    }

    public static void setup() {
        gameOver = false;
        direction = "STOP";
        x = WIDTH / 2;
        y = HEIGHT / 2;
        Random random = new Random();
        fruitX = random.nextInt(WIDTH);
        fruitY = random.nextInt(HEIGHT);
        score = 0;
    }

    public static void draw() {
        StringBuilder board = new StringBuilder();

        // Draw top wall
        for (int i = 0; i < WIDTH + 2; i++) board.append(WALL);
        board.append("\n");

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (j == 0) board.append(WALL); // Left wall

                if (i == y && j == x)
                    board.append(SNAKE_HEAD); // Snake head
                else if (i == fruitY && j == fruitX)
                    board.append(FRUIT); // Fruit
                else {
                    boolean printTail = false;
                    for (int k = 0; k < nTail; k++) {
                        if (tailX[k] == j && tailY[k] == i) {
                            board.append(SNAKE_BODY);
                            printTail = true;
                            break;
                        }
                    }
                    if (!printTail) board.append(EMPTY_SPACE);
                }

                if (j == WIDTH - 1) board.append(WALL); // Right wall
            }
            board.append("\n");
        }

        // Draw bottom wall
        for (int i = 0; i < WIDTH + 2; i++) board.append(WALL);
        board.append("\nScore: ").append(score);

        // Print the board in one go
        System.out.print("\033[H\033[2J"); // Clear the console
        System.out.flush();
        System.out.println(board);
    }

    public static void input() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (!gameOver) {
                if (reader.ready()) {
                    String key = reader.readLine().toUpperCase();
                    switch (key) {
                        case "A":
                            if (!direction.equals("RIGHT")) direction = "LEFT";
                            break;
                        case "D":
                            if (!direction.equals("LEFT")) direction = "RIGHT";
                            break;
                        case "W":
                            if (!direction.equals("DOWN")) direction = "UP";
                            break;
                        case "S":
                            if (!direction.equals("UP")) direction = "DOWN";
                            break;
                        case "X":
                            gameOver = true;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logic() {
        int prevX = tailX[0];
        int prevY = tailY[0];
        int prev2X, prev2Y;
        tailX[0] = x;
        tailY[0] = y;

        for (int i = 1; i < nTail; i++) {
            prev2X = tailX[i];
            prev2Y = tailY[i];
            tailX[i] = prevX;
            tailY[i] = prevY;
            prevX = prev2X;
            prevY = prev2Y;
        }

        switch (direction) {
            case "LEFT" -> x--;
            case "RIGHT" -> x++;
            case "UP" -> y--;
            case "DOWN" -> y++;
        }

        // Check for wall collision
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) gameOver = true;

        // Check if snake hits itself
        for (int i = 0; i < nTail; i++) {
            if (tailX[i] == x && tailY[i] == y) gameOver = true;
        }

        // Check if snake eats fruit
        if (x == fruitX && y == fruitY) {
            score += 10;
            Random random = new Random();
            fruitX = random.nextInt(WIDTH);
            fruitY = random.nextInt(HEIGHT);
            nTail++;
        }
    }
}


