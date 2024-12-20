import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum eDirection { STOP, LEFT, RIGHT, UP, DOWN }

public class Game extends Snake implements IGame {
    protected eDirection dir; // Направление движения змеи
    protected Score score;     // Счет игры
    protected boolean gameover; // Флаг завершения игры
    protected int xHead, yHead, fruitX, fruitY;
    private static int gameCount;
    private List<Integer> history; // Добавлено для хранения истории игр

    public Game() {
        super();
        Random rand = new Random();
        gameCount++;
        gameover = false;
        dir = eDirection.STOP;
        xHead = (this.width / 2) - 1;
        yHead = (this.height / 2) - 1;
        fruitX = rand.nextInt(this.width - 1);
        fruitY = rand.nextInt(this.height);
        score = new Score();
        history = new ArrayList<>(); // Инициализация истории
    }

    public static int getGameCount() {
        return gameCount;
    }

    @Override
    public int getScore() {
        return score.getScore();
    }

    @Override
    public boolean isGameOver() {
        return gameover;
    }

    @Override
    public void draw() {
        // Очистка консоли
        System.out.print('\u000C');
        drawHorizontalBorders();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (drawVerticalBorders(i, j)) continue;
                if (drawHeadOfSnake(i, j)) continue;
                if (drawFruit(i, j)) continue;
                drawSnakeTailOrSpace(i, j);
            }
            System.out.println();
        }
        drawHorizontalBorders();
        System.out.println("Score: " + getScore());
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
            System.out.print("O"); // Голова змеи
            return true;
        }
        return false;
    }

    private boolean drawFruit(int y, int x) {
        if (y == fruitY && x == fruitX) {
            System.out.print("F"); // Фрукт
            return true;
        }
        return false;
    }

    private void drawSnakeTailOrSpace(int y, int x) {
        boolean print = false;
        for (int k = 0; k < nTail; k++) {
            if (tailX[k] == x && tailY[k] == y) {
                print = true;
                System.out.print("o"); // Тело змеи
            }
        }
        if (!print) System.out.print(' '); // Пустое пространство
    }

    @Override
    public void logic() {
        tailStep();
        changeHeadPosition();
        meetingWithBorder();
        headToTailCheck();
        eatingFruits();
        history.add(score.getScore()); // Сохраняем текущий счет в историю
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
            fruitX = new Random().nextInt(this.width - 1);
            fruitY = new Random().nextInt(this.height);
            if (nTail > 0) while (checkTailAndFruitCoincidence());
            nTail++;
        }
    }

    public List<Integer> getHistory() {
        return history;
    }
}