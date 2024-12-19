import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AdvancedGame[] games = new AdvancedGame[100];
        int[] scores = new int[100];
        int gameCount = 0;

        for (int i = 0; i < 100; i++) {
            games[i] = new AdvancedGame(); // Создание объекта производного класса
            while (!games[i].isGameOver() && !games[i].isGameSet()) {
                games[i].draw();
                games[i].input();
                games[i].logic();
                try {
                    Thread.sleep(300); // Задержка на 300 миллисекунд
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Game Over for game " + (i + 1) + "! Your score: " + games[i].getScore());
            scores[i] = games[i].getScore();
            gameCount++;
            if (games[i].isGameSet()) break;
        }

        // Сортировка результатов
        Arrays.sort(scores, 0, gameCount);

        // Display sorted scores
        System.out.print("Sorted Scores: ");
        for (int i = 0; i < gameCount; i++) {
            System.out.print(scores[i] + " ");
        }
        System.out.println();

        // Поиск конкретного результата
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a score to search for: ");
        int scoreToFind = scanner.nextInt();

        if (Arrays.binarySearch(scores, 0, gameCount, scoreToFind) >= 0) {
            System.out.println("Score " + scoreToFind + " found in the list.");
        } else {
            System.out.println("Score " + scoreToFind + " not found in the list.");
        }

        System.out.println("Total number of games created: " + AdvancedGame.getGameCount());
    }
}