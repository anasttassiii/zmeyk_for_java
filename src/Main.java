import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

enum eDirection { STOP, LEFT, RIGHT, UP, DOWN }

class Score {
    private int score;

    public Score() {
        this.score = 0;
    }

    public void addScore(int value) {
        this.score += value;
    }

    public int getScore() {
        return this.score;
    }
}

class Snake {
    protected int width;
    protected int height;
    protected int[] tailX;
    protected int[] tailY;
    protected int nTail;

    public Snake(int width, int height) {
        this.width = width;
        this.height = height;
        this.tailX = new int[100]; // Максимальная длина хвоста
        this.tailY = new int[100];
        this.nTail = 0;
    }
}

class Game extends Snake {
    private static int gameCount = 0; // Статическое поле для отслеживания количества объектов Game
    private eDirection dir;
    private boolean gameover;
    private int xHead, yHead, fruitX, fruitY;
    private Score score; // Использование вспомогательного класса для счета
    private static final Random rand = new Random();
    private Scanner scanner = new Scanner(System.in);

    public Game(int width, int height) {
        super(width, height);
        gameCount++;
        this.dir = eDirection.STOP;
        this.gameover = false;
        this.xHead = width / 2;
        this.yHead = height / 2;
        this.fruitX = rand.nextInt(width);
        this.fruitY = rand.nextInt(height);
        this.score = new Score(); // Инициализация объекта Score
    }

    public boolean isGameover() {
        return gameover;
    }

    public static int getGameCount() {
        return gameCount; // Возвращаем текущее количество игровых объектов
    }

    public int getScore() {
        return score.getScore(); // Возвращаем текущий счет из объекта Score
    }

    public void input() {
        if (scanner.hasNext()) {
            String command = scanner.next();
            command = command.trim().toUpperCase(); // Убираем пробелы и приводим к верхнему регистру
            switch (command) {
                case "W": dir = eDirection.UP; break;
                case "S": dir = eDirection.DOWN; break;
                case "A": dir = eDirection.LEFT; break;
                case "D": dir = eDirection.RIGHT; break;
                case "Q": gameover = true; break;
                case "LEVEL":
                    System.out.println("Choose difficulty (1- easy, 2- hard): ");
                    String level = scanner.next().trim();
                    if (level.equals("1")) {
                        System.out.println("Difficulty set to easy.");
                    } else if (level.equals("2")) {
                        System.out.println("Difficulty set to hard.");
                    } else {
                        System.out.println("Invalid choice. Default difficulty applied.");
                    }
                    break;
                default: break;
            }
        }
    }

    public void logic() {
        int prevX = tailX[0];
        int prevY = tailY[0];
        int prev2X, prev2Y;
        tailX[0] = xHead;
        tailY[0] = yHead;

        for (int i = 1; i < nTail; i++) {
            prev2X = tailX[i];
            prev2Y = tailY[i];
            tailX[i] = prevX;
            tailY[i] = prevY;
            prevX = prev2X;
            prevY = prev2Y;
        }

        switch (dir) {
            case LEFT: xHead--; break;
            case RIGHT: xHead++; break;
            case UP: yHead--; break;
            case DOWN: yHead++; break;
            default: break;
        }

        if (xHead >= width) xHead = 0;
        else if (xHead < 0) xHead = width - 1;
        if (yHead >= height) yHead = 0;
        else if (yHead < 0) yHead = height - 1;

        for (int i = 0; i < nTail; i++) {
            if (tailX[i] == xHead && tailY[i] == yHead) {
                gameover = true;
            }
        }

        if (xHead == fruitX && yHead == fruitY) {
            score.addScore(10); // Используем метод для увеличения счета
            fruitX = rand.nextInt(width);
            fruitY = rand.nextInt(height);
            nTail++;
        }
    }

    public void draw() {
        clearConsole();
        for (int i = 0; i < width + 2; i++) {
            System.out.print("#");
        }
        System.out.println();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j == 0) System.out.print("#");
                if (i == yHead && j == xHead) System.out.print("O");
                else if (i == fruitY && j == fruitX) System.out.print("F");
                else {
                    boolean printTail = false;
                    for (int k = 0; k < nTail; k++) {
                        if (tailX[k] == j && tailY[k] == i) {
                            System.out.print("o");
                            printTail = true;
                        }
                    }
                    if (!printTail) System.out.print(" ");
                }
                if (j == width - 1) System.out.print("#");
            }
            System.out.println();
        }
        for (int i = 0; i < width + 2; i++) {
            System.out.print("#");
        }
        System.out.println("\nScore: " + score.getScore());
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

public class Main {
    public static void main(String[] args) {
        Game[] games = new Game[3]; // Массив объектов Game

        for (int i = 0; i < games.length; i++) {
            games[i] = new Game(20, 15);
            while (!games[i].isGameover()) {
                games[i].draw();
                games[i].input();
                games[i].logic();
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Game Over for game " + (i + 1) + "! Your score: " + games[i].getScore());
        }
        System.out.println("Total number of games created: " + Game.getGameCount()); // Статическое использование
    }
}