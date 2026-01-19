public class Main {
    public static void main(String[] args) {
        Minesweeper m = new Minesweeper(20, 10, 15);

        System.out.println(m);
        System.out.println(m.draw());

        m.reveal(1,1);
        System.out.println(m.getState());
        System.out.println(m.draw());
    }
}