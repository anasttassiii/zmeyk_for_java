public class Score {
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