import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class Minesweeper {
    private final int[][] fields;
    private final int rows;
    private final int columns;
    private final int bombs;


    public Minesweeper(int rows, int columns, int bombs){
        if (bombs > rows * columns) {
            throw new IllegalArgumentException("Bombas demais para o tabuleiro");
        }
        this.rows=rows;
        this.columns=columns;
        this.bombs=bombs;
        this.fields=new int[rows][columns];
        Set<Cell> setOfBombs = generateBombs();
        addNumberAdj(setOfBombs);
    }

    private Set<Cell> generateBombs(){
        Set<Cell> setOfBombs = new HashSet<Cell>();
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
