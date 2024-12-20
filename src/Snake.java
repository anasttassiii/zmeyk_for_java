public class Snake {
    protected int width;
    protected int height;
    protected int[] tailX;
    protected int[] tailY;
    protected int nTail;

    public Snake() {
        this.width = 20;
        this.height = 15;
        this.tailX = new int[100];
        this.tailY = new int[100];
        nTail = 0;
    }
}
