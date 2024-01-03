import java.util.Random;

/**
 * Provides the Grid object for the whackamole game
 * @author William Baldwin
 */
public class WhackaMole {
    /**
     * number of rows
     */
    public static final int ROWS = 5;

    /**
     * number of columns
     */
    public static final int COLS = 5;

    /**
     * symbol names
     */
    public static final String[][] SYMBOL_NAMES = {{"cat", "dog", "tiger", "frog", "cat"}, 
        {"tiger", "lion", "dog", "tiger", "frog"},
        {"lion", "frog", "mole",  "dog", "cat"},
        {"frog", "dog", "tiger", "cat", "lion"},
        {"cat", "frog", "lion", "dog", "tiger"}};
                                                    
    /**
     * symbol points
     */
    public static final int[][] SYMBOL_POINTS = {{10, 15, 30, 20, 10},
        {30, 40, 15, 30, 20},
        {40, 20, 50, 15, 10},
        {20, 15, 30, 10, 40},
        {10, 20, 40, 15, 30}};


    
    /**
     *  testing state
    */                                            
    private boolean testing;

    /**
     *  total score
    */
    private int totalScore;

    /**
     *  # of misses
    */
    private int numberOfMisses;

    /**
     *  grid
    */
    private Grid grid;

    /**
     *  random var
    */
    private Random rand;

    /**
     *  next row #
    */
    private int nextRow;

    /**
     *  next Column #
    */
    private int nextCol;

    /**
     *  whackamole constructor
     * @param testing test mode
    */
    public WhackaMole(boolean testing) {
        this.testing = testing;
        
        Grid gridx = new Grid(ROWS, COLS);
        
        for (int j = 0; j < ROWS; j++) {
            for (int i = 0; i < COLS; i++) {
                Symbol x = new Symbol(SYMBOL_NAMES[j][i], SYMBOL_POINTS[j][i]);
                gridx.setSymbol(j, i, x);
            }
        }
        if (testing == true) {
            nextRow = 0;
            nextCol = 0;
        } else {
            rand = new Random();
        }
        this.grid = gridx;
    }

    /**
     * get total score
     * @return total score
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * get number of misses
     * @return number of misses
     */
    public int getNumberOfMisses() {
        return numberOfMisses;
    }

    /**
     * get next row
     * @return next row number
     */
    public int getNextRow() {
        return nextRow;
    }

    /**
     * get next column
     * @return next column number
     */
    public int getNextCol() {
        return nextCol;
    }

    /**
     * get symbol name
     * @param row row #
     * @param col column #
     * @return symbol name
     * @throws IllegalArgumentException invalid rows or columns
     */
    public String getSymbolName(int row, int col) {
        if (row < 0 || row >= ROWS) {
            throw new IllegalArgumentException("Invalid row");
        }
        if (col < 0 || col >= COLS) {
            throw new IllegalArgumentException("Invalid col");
        }
        return (grid.getSymbol(row, col)).getName();
    }

    /**
     * get symbol points
     * @param row row #
     * @param col column #
     * @return symbol points
     * @throws IllegalArgumentException invalid rows or columns
     */
    public int getSymbolPoints(int row, int col) {
        if (row < 0 || row >= ROWS) {
            throw new IllegalArgumentException("Invalid row");
        }
        if (col < 0 || col >= COLS) {
            throw new IllegalArgumentException("Invalid col");
        }
        return (grid.getSymbol(row, col)).getPoints();
    }

    /**
     * if a symbol has been clicked on
     * @param row row #
     * @param col column #
     * @return true/false has been clicked on
     * @throws IllegalArgumentException invalid rows or columns
     */
    public boolean hasBeenClickedOn(int row, int col) {
        if (row < 0 || row >= ROWS) {
            throw new IllegalArgumentException("Invalid row");
        }
        if (col < 0 || col >= COLS) {
            throw new IllegalArgumentException("Invalid col");
        }
        if ((grid.getSymbol(row, col)).hasBeenClickedOn()) {
            return true;
        }
        return false;
    }

    /**
     * says whether or not all symbols have been clicked
     * @return true/false all symbolsclicked on
     */
    public boolean allSymbolsClickedOn() {
        for (int j = 0; j < ROWS; j++) {
            for (int i = 0; i < COLS; i++) {
                if (!(grid.getSymbol(j, i)).hasBeenClickedOn()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * updates the next row/col
     */
    private void updateNextRowAndCol() {
        if (allSymbolsClickedOn()) {
            nextRow = -1;
            nextCol = -1;
        } else {
            if (testing == true) {
                if (nextCol == COLS - 1) {
                    nextRow += 1;
                    nextCol = 0;
                } else {
                    nextCol += 1;
                }
            } else {
                nextRow = rand.nextInt(ROWS);
                nextCol = rand.nextInt(COLS);
            }
        }
    }

    /**
     * clicks on symbol
     * @param row row #
     * @param col column #
     * @throws IllegalArgumentException invalid rows or columns
     */
    public void clickOnSymbol(int row, int col) {
        if (row < 0 || row >= ROWS) {
            throw new IllegalArgumentException("Invalid row");
        }
        if (col < 0 || col >= COLS) {
            throw new IllegalArgumentException("Invalid col");
        }
        if (!(grid.getSymbol(row, col).hasBeenClickedOn())) {
            grid.getSymbol(row, col).setHasBeenClickedOn(true);
            totalScore += grid.getSymbol(row, col).getPoints();
            updateNextRowAndCol();
        }
    }

    /**
     * adds a miss
     */
    public void addMiss() {
        numberOfMisses += 1;
        updateNextRowAndCol();
    }

    /**
     * returns grid
     * @return grid
     */
    public Grid getGrid() {
        return grid;
    }


}



