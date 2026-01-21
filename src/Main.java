import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int rows = sc.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = sc.nextInt();
        System.out.print("Enter number of bombs: ");
        int bombs = sc.nextInt();

        Minesweeper minesweeper = new Minesweeper(rows, cols, bombs);

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