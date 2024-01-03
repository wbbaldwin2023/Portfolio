/**
 * Provides the Grid object for the whackamole game
 * @author William Baldwin
 */
public class Grid {

    /**
     * rows
     */
    private int rows;

    /**
     * cols
     */
    private int cols;

    /**
     * symbols object array
     */
    private Symbol[][] symbols;

    /**
     * grid constructor
     * @param rows rows
     * @param cols columns
     * @throws IllegalArgumentException if rows or cols < 1
     */
    public Grid(int rows, int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("Invalid rows/cols");
        }
        this.rows = rows;
        this.cols = cols;
        symbols = new Symbol [rows] [cols];
    }

    /**
     * returns rows
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * returns cols
     * @return columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * sets symbol to import
     * @param row rows
     * @param col columns
     * @param symbol Symbol
     * @throws IllegalArgumentException for null symbol, or invalid row/col
     */
    public void setSymbol(int row, int col, Symbol symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("Null symbol");
        }
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Invalid row");
        }
        if (col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid col");
        }
        symbols [row] [col] = symbol;

    }

    /**
     * returns symbol from array
     * @param row rows
     * @param col columns
     * @return symbol
     * @throws IllegalArgumentException invalid row/col
     */
    public Symbol getSymbol(int row, int col) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Invalid row");
        }
        if (col < 0 || col >= cols) {
            throw new IllegalArgumentException("Invalid col");
        }
        return symbols [row] [col];
    }

    /**
     * returns string of symbols
     * @return string
     */
    public String toString() {

        String gs = "";

        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                gs += symbols[j][i].getName();
                if (i < cols - 1) {
                    gs += " ";
                } else {
                    if (j < rows) {
                        gs += "\n";
                    }
                }
            }
        }
        return gs;
    }

}