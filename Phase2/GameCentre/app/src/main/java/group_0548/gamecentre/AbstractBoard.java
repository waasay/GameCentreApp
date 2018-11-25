package group_0548.gamecentre;

import java.io.Serializable;
import java.util.Arrays;


/**
 * Abstract class for other board objects
 */

public abstract class AbstractBoard implements Serializable {
    /**
     * The number of rows.
     */
    private int numRow;

    /**
     * The number of columns.
     */
    private int numCol;

    /**
     * The tiles on the board in row-major order.
     */
    private AbstractTile[][] tiles;


    abstract public int numTiles();


    abstract public AbstractTile getTile(int row, int col);

    abstract public AbstractTile[][] getTiles();



    /**
     * Get the current row number.
     *
     * @return the row number.
     */
    public abstract int getNumRow();

    /**
     * Get the current column number.
     *
     * @return the current column number.
     */
    public abstract int getNumCol();


    public String toString(){
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';

    }
}
