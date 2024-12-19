import java.util.ArrayList;

class AdvancedGame extends Game {
    private ArrayList<Integer> history;

    public AdvancedGame() {
        super(); // Вызов конструктора базового класса
        history = new ArrayList<>();
    }

    public void saveScore() {
        history.add(getScore()); // Сохранение текущего счета
    }

    public void displayHistory() {
        System.out.println("Game History: " + history);
    }

    @Override
    public void draw() {
        super.draw(); // Вызов метода draw родителя
        displayHistory(); // Отображение истории
    }

    // Метод для глубокого клонирования
    public AdvancedGame deepClone() {
        AdvancedGame clone = new AdvancedGame();
        clone.score = new Score(); // Клонируем внутренний объект Score
        clone.xHead = this.xHead;
        clone.yHead = this.yHead;
        clone.fruitX = this.fruitX;
        clone.fruitY = this.fruitY;
        clone.nTail = this.nTail;
        System.arraycopy(this.tailX, 0, clone.tailX, 0, this.tailX.length);
        System.arraycopy(this.tailY, 0, clone.tailY, 0, this.tailY.length);
        return clone;
    }
}