import java.util.*;

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
        Set<Position> setOfBombs = generateBombs();
        addNumberAdj(setOfBombs);
    }

    private Set<Position> generateBombs(){
        Set<Position> setOfBombs = new HashSet<>();
        Random rand = new Random();
        int placed=0;

        while(placed<bombs){
            Position p = new Position(rand.nextInt(rows), rand.nextInt(columns));
            if(setOfBombs.add(p)){
                placed++;
            }
        }
        return setOfBombs;
    }

    private void addNumberAdj(Set<Position> setOfBombs){
        for(Position p: setOfBombs){
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

        if(fields[row][column]==-1){
            state = GameState.LOST;
            revealed[row][column]=true;
            return;
        } else if(fields[row][column]==0) {
            revealAdj(row, column);
        } else {
            revealed[row][column] = true;
            revealedCount++;
        }

        if(hasWon()){
            state = GameState.WON;
        }
    }

    private void revealAdj(int startRow, int startCol) {
        Queue<Position> queue = new ArrayDeque<>();
        queue.add(new Position(startRow, startCol));

        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            int r = pos.getRow();
            int c = pos.getColumn();

            if (!isInsideBoard(r, c) || revealed[r][c] || fields[r][c] == -1) {
                continue;
            }
            revealed[r][c] = true;
            revealedCount++;

            if (fields[r][c] == 0) {
                for (int i = r - 1; i <= r + 1; i++) {
                    for (int j = c - 1; j <= c + 1; j++) {
                        if (isInsideBoard(i, j) && !revealed[i][j] && fields[i][j] != -1) {
                            queue.add(new Position(i, j));
                        }
                    }
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

    public String buildBoardString(boolean showOnlyRevealed) {
        StringBuilder result = new StringBuilder();
        for(int i=0;i<rows;i++){
            result.append("[");
            for(int j=0; j<columns; j++){
                if(!showOnlyRevealed || revealed[i][j]) {
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

    public String draw(){
        return buildBoardString(true);
    }

    @Override
    public String toString(){
        return buildBoardString(false);

    }

}
