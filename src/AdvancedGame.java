import java.util.List;

public class AdvancedGame extends Game {
    public AdvancedGame() {
        super();
    }

    @Override
    public void draw() {
        super.draw(); // Вызов метода базового класса
        displayHistory(); // Отображение истории
    }

    public void displayHistory() {
        System.out.print("Game History: ");
        for (int score : getHistory()) {
            System.out.print(score + " ");
        }
        System.out.println();
    }
}