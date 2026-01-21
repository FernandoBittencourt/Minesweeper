import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Minesweeper minesweeper = new Minesweeper(10, 10, 5);
        System.out.println(minesweeper.draw());
        while(GameState.RUNNING.equals(minesweeper.getState())) {
            System.out.print("Enter row and column (e.g., 0 1): ");
            int row = sc.nextInt();
            int col = sc.nextInt();
            try {
                minesweeper.reveal(row, col);
                System.out.println(minesweeper.draw());
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println(minesweeper.draw());
        System.out.println("Game " + minesweeper.getState());
        sc.close();
    }
}