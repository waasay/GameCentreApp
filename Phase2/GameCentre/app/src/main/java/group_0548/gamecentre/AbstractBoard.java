package group_0548.gamecentre;

import java.io.Serializable;
import java.util.Arrays;


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
     * @return
     */
    public int numTiles(){
        return this.numCol*this.numRow;
    }


    /**
     * Return the tile at (row, col)
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
     public T getTile(int row, int col){
        return this.tiles[row][col];
    }

    /**
     * Getter for tiles
     */
    public T[][] getTiles(){
        return this.tiles;
    }

    /**
     * Setter for tiles
     */
    public void setTiles(T[][] newTiles){
        this.tiles = newTiles;
    }


    /**
     * Get the current row number.
     *
     * @return the row number.
     */
    public int getNumRow(){
        return this.numRow;
    }

    /**
     * Get the current column number.
     *
     * @return the current column number.
     */
    public int getNumCol(){
        return this.numCol;
    }



}
