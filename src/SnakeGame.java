import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final char EMPTY_CELL = '.';
    private static final char SNAKE_BODY = 'O';
    private static final char FOOD = 'X';

    private List<int[]> snake;
    private int[] food;
    private int direction;
    private Random random;
    private boolean gameOver;

    public SnakeGame() {
        snake = new ArrayList<>();
        snake.add(new int[]{WIDTH / 2, HEIGHT / 2});
        food = new int[2];
        random = new Random();
        direction = 0;
        gameOver = false;
    }

    public void run() {
        while (!gameOver) {
            update();
            draw();
            handleInput();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Game Over!");
    }

    private void update() {
        int[] head = snake.get(0).clone();
        switch (direction) {
            case 0:
                head[0]++;
                break;
            case 1:
                head[1]++;
                break;
            case 2:
                head[0]--;
                break;
            case 3:
                head[1]--;
                break;
        }

        if (head[0] < 0 || head[0] >= WIDTH || head[1] < 0 || head[1] >= HEIGHT) {
            gameOver = true;
            return;
        }

        for (int i = 1; i < snake.size(); i++) {
            int[] segment = snake.get(i);
            if (head[0] == segment[0] && head[1] == segment[1]) {
                gameOver = true; 
                return;
            }
        }

        snake.add(0, head);

        if (head[0] == food[0] && head[1] == food[1]) {
            generateFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    private void generateFood() {
        do {
            food[0] = random.nextInt(WIDTH);
            food[1] = random.nextInt(HEIGHT);
        } while (isSnakeCell(food[0], food[1]));
    }

    private boolean isSnakeCell(int x, int y) {
        for (int[] segment : snake) {
            if (segment[0] == x && segment[1] == y) {
                return true;
            }
        }
        return false;
    }

    private void draw() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (isSnakeCell(x, y)) {
                    System.out.print(SNAKE_BODY);
                } else if (x == food[0] && y == food[1]) {
                    System.out.print(FOOD);
                } else {
                    System.out.print(EMPTY_CELL);
                }
            }
            System.out.println();
        }
    }

    private void handleInput() {
        Scanner scanner = new Scanner(System.in);
        char input = scanner.nextLine().charAt(0);
        switch (input) {
            case 'w':
                direction = 3;
                break;
            case 'a':
                direction = 2;
                break;
            case 's':
                direction = 1;
                break;
            case 'd':
                direction = 0;
                break;
        }
    }

    public static void main(String[] args) {
        SnakeGame game = new SnakeGame();
        game.run();
    }
}
