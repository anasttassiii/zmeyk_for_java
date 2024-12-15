import java.util.Random;
import java.util.Scanner;

class Game extends Snake {
    public Direction dir;
    public Score score;
    public boolean gameover, gameset;
    public int xHead, yHead, fruitX, fruitY;
    private static int gameCount = 0;
    private static final Random random = new Random();

    public Game() {
        gameCount++;
        gameover = false;
        gameset = false;
        dir = Direction.STOP;
        xHead = (this.width / 2) - 1;
        yHead = (this.height / 2) - 1;
        fruitX = random.nextInt(this.width - 1);
        fruitY = random.nextInt(this.height);
        score = new Score();
    }

    public static int getGameCount() {
        return gameCount;
    }

    public int getScore() {
        return score.getScore();
    }

    public void draw() {
        clearScreen();
        drawHorizontalBorders();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (!drawVerticalBorders(i, j)) {
                    if (!drawHeadOfSnake(i, j)) {
                        if (!drawFruit(i, j)) {
                            drawSnakeTailOrSpace(i, j);
                        }
                    }
                }
            }
            System.out.println();
        }
        drawHorizontalBorders();
        System.out.println("Score: " + getScore());
    }

    public void input() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input) {
                case "a": dir = Direction.LEFT; break;
                case "d": dir = Direction.RIGHT; break;
                case "w": dir = Direction.UP; break;
                case "s": dir = Direction.DOWN; break;
                case "x": gameover = true; break;
                case "e": gameset = true; break;
                default: break;
            }
        }
    }

    public void logic() {
        tailStep();
        changeHeadPosition();
        meetingWithBorder();
        headToTailCheck();
        eatingFruits();
    }

    private void drawHorizontalBorders() {
        for (int i = 0; i < width + 1; i++) {
            System.out.print("#");
        }
        System.out.println();
    }

    private boolean drawVerticalBorders(int y, int x) {
        if (x == 0 || x == width - 1) {
            System.out.print('#');
            return true;
        }
        return false;
    }

    private boolean drawHeadOfSnake(int y, int x) {
        if (y == yHead && x == xHead) {
            System.out.print("0");
            return true;
        }
        return false;
    }

    private boolean drawFruit(int y, int x) {
        if (y == fruitY && x == fruitX) {
            System.out.print("F");
            return true;
        }
        return false;
    }

    private void drawSnakeTailOrSpace(int y, int x) {
        boolean print = false;
        for (int k = 0; k < nTail; k++) {
            if (tailX[k] == x && tailY[k] == y) {
                print = true;
                System.out.print("o");
            }
        }
        if (!print) System.out.print(' ');
    }

    private void tailStep() {
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
    }

    private void changeHeadPosition() {
        switch (dir) {
            case LEFT: xHead--; break;
            case RIGHT: xHead++; break;
            case UP: yHead--; break;
            case DOWN: yHead++; break;
        }
    }

    private void meetingWithBorder() {
        if (xHead >= width - 1) xHead = 0;
        else if (xHead < 0) xHead = width - 2;
        if (yHead >= height) yHead = 0;
        else if (yHead < 0) yHead = height - 1;
    }

    private void headToTailCheck() {
        for (int i = 0; i < nTail; i++) {
            if (tailX[i] == xHead && tailY[i] == yHead) gameover = true;
        }
    }

    private boolean checkTailAndFruitCoincidence() {
        for (int i = 0; i < nTail; i++) {
            if (tailX[i] == fruitX && tailY[i] == fruitY) {
                return true;
            }
        }
        return false;
    }

    private void eatingFruits() {
        if (xHead == fruitX && yHead == fruitY) {
            score.addScore(10);
            fruitX = random.nextInt(width - 1);
            fruitY = random.nextInt(height);
            if (nTail > 0) while (checkTailAndFruitCoincidence());
            nTail++;
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}