import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Minesweeper minesweeper = new Minesweeper(20, 10, 15);
        while(GameState.RUNNING.equals(minesweeper.getState())) {
            System.out.println(minesweeper.draw());
            System.out.print("Enter row and column (e.g., 0 1): ");
            int row = sc.nextInt();
            int col = sc.nextInt();
            minesweeper.reveal(row, col);
        }
        System.out.println(minesweeper.draw());
        System.out.println("Game " + minesweeper.getState());
        sc.close();
    }
}