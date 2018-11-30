package group_0548.gamecentre;

import java.io.Serializable;


/**
 * Abstract class for other board objects
 */

public abstract class AbstractBoard<T> implements Serializable {
    /**
     * The number of rows.
     */
    protected int numRow;

    /**
     * The number of columns.
     */
    protected int numCol;

    /**
     * The tiles on the board in row-major order.
     */
    protected T[][] tiles;

    /**
     * Return the number of Tiles in this abstractboard
     *
     * @return the number of tiles
     */
    public int numTiles() {
        return this.numCol * this.numRow;
    }


    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public abstract T getTile(int row, int col);


    /**
     * Getter for tiles
     */
    public abstract T[][] getTiles();

    /**
     * Setter for tiles
     */
    public abstract void setTiles(T[][] newTiles);


    /**
     * Get the current row number.
     *
     * @return the row number.
     */
    public int getNumRow() {
        return this.numRow;
    }

    /**
     * Get the current column number.
     *
     * @return the current column number.
     */
    public int getNumCol() {
        return this.numCol;
    }


}
