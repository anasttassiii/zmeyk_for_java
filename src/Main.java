import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AdvancedGame game = new AdvancedGame();
        Scanner scanner = new Scanner(System.in);

        while (!game.isGameOver()) {
            game.draw();
            System.out.print("Enter your move (w/a/s/d to move, x to exit): ");
            char input = scanner.nextLine().charAt(0); // Обработка ввода пользователя

            switch (input) {
                case 'a': game.dir = eDirection.LEFT; break;
                case 'd': game.dir = eDirection.RIGHT; break;
                case 'w': game.dir = eDirection.UP; break;
                case 's': game.dir = eDirection.DOWN; break;
                case 'x': game.gameover = true; break; // Выход из игры
            }

            // Логика игры
            game.logic();
        }

        System.out.println("Game Over! Your score: " + game.getScore());
        scanner.close();
    }
}