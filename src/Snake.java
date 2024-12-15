import java.util.Random;

enum Direction { STOP, LEFT, RIGHT, UP, DOWN }

class Score {
    private int score;

    public Score() {
        this.score = 0;
    }

    public void addScore(int value) {
        score += value;
    }

    public int getScore() {
        return score;
    }
}

class Snake {
    protected int width;
    protected int height;
    protected int[] tailX = new int[100];
    protected int[] tailY = new int[100];
    protected int nTail;

    public Snake() {
        this.width = 20;
        this.height = 15;
        this.nTail = 0;
    }
}