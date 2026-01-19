import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class Minesweeper {

    private final int[][] fields;
    private final boolean[][] revealed;
    private int revealedCount;
    private final int rows;
    private final int columns;
    private final int bombs;
    private GameState state;

    public Minesweeper(int rows, int columns, int bombs){
        if (bombs > rows * columns) {
            throw new IllegalArgumentException("Too many bombs for the board.");
        }
        this.rows=rows;
        this.columns=columns;
        this.bombs=bombs;
        state = GameState.RUNNING;
        this.fields=new int[rows][columns];
        this.revealed=new boolean[rows][columns];
        this.revealedCount=0;
        Set<Cell> setOfBombs = generateBombs();
        addNumberAdj(setOfBombs);
    }

    private Set<Cell> generateBombs(){
        Set<Cell> setOfBombs = new HashSet<>();
        Random rand = new Random();
        int placed=0;

        while(placed<bombs){
            Cell p = new Cell(rand.nextInt(rows), rand.nextInt(columns));
            if(setOfBombs.add(p)){
                placed++;
            }
        }
        return setOfBombs;
    }

    private void addNumberAdj(Set<Cell> setOfBombs){
        for(Cell p: setOfBombs){
            fields[p.getRow()][p.getColumn()]=-1;
            for(int row=p.getRow()-1; row<=p.getRow()+1; row++){
                for(int column=p.getColumn()-1; column<=p.getColumn()+1; column++){
                    if(column>=0 && column<columns && row>=0 && row<rows){
                        if(fields[row][column] != -1){
                            fields[row][column]++;
                        }
                    }
                }
            }
        }
    }

    public void reveal(int row, int column){
        if(!GameState.RUNNING.equals(state)){
            throw new IllegalArgumentException("The game is finished.");
        }
        if(!isInsideBoard(row, column)) {
            throw new IllegalArgumentException("Out of the board.");
        }
        if (revealed[row][column]) {
            throw new IllegalArgumentException("Cell already revealed.");
        }
        revealed[row][column]=true;
        revealedCount++;

        if(fields[row][column]==-1){
            state = GameState.LOST;
            return;
        } else if(fields[row][column]==0) {
            revealAdj(row, column);
        }
        if(hasWon()){
            state = GameState.WON;
        }
    }

    private void revealAdj(int row, int column) {
        // TODO: Replace DFS with BFS to avoid deep recursion (stack overflow).
        for(int i=row-1; i<=row+1; i++){
            for(int j=column-1; j<=column+1; j++){
                if(!isInsideBoard(i,j) || revealed[i][j] || fields[i][j]==-1) {
                    continue;
                }
                revealed[i][j]=true;
                revealedCount++;
                if(fields[i][j]==0) {
                    revealAdj(i, j);
                }
            }
        }
    }
    private boolean hasWon(){
        return revealedCount==(rows*columns)-bombs;
    }
    private boolean isInsideBoard(int row, int column){
        return row >= 0 && row < rows && column >= 0 && column<columns;
    }

    public String draw(){
        StringBuilder result = new StringBuilder();
        for(int i=0;i<rows;i++){
            result.append("[");
            for(int j=0; j<columns; j++){
                if(revealed[i][j]) {
                    if (fields[i][j] == -1) {
                        result.append("*");
                    } else {
                        result.append(fields[i][j]);
                    }
                } else {
                    result.append("-");
                }

                if (j < columns - 1) {
                    result.append(",");
                }
            }

            result.append("]\n");
        }
        return result.toString();
    }

    public GameState getState(){
        return state;
    }
    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        for(int i=0;i<rows;i++){
            result.append("[");
            for(int j=0; j<columns; j++){
                if(fields[i][j]==-1){
                    result.append("*");
                } else {
                    result.append(fields[i][j]);
                }
                if(j<columns-1){
                    result.append(",");
                }
            }

            result.append("]\n");
        }
        return result.toString();
    }

}
