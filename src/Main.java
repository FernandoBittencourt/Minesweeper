public class Main {
    public static void main(String[] args) {
        Minesweeper m = new Minesweeper(20, 10, 15);

        System.out.println(m);
        System.out.println(m.draw());

        System.out.println(m.reveal(1,1));
        //System.out.println(m.reveal(2,3));
        //System.out.println(m.reveal(0,3));
        System.out.println(m.draw());
    }
}