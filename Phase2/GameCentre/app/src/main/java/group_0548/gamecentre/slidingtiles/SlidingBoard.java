package group_0548.gamecentre.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import group_0548.gamecentre.AbstractBoard;


/**
 * The sliding tiles board.
 */
public class SlidingBoard extends AbstractBoard<SlidingTile> implements Serializable, Iterable<SlidingTile> {


    /**
     * The tiles on the board in row-major order.
     */
    private SlidingTile[][] tiles;


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tilesBoard the tiles for the board
     */

    SlidingBoard(List<SlidingTile> tilesBoard, int rowNum, int colNum) {
        tiles = new SlidingTile[rowNum][colNum];
        Iterator<SlidingTile> iterator = tilesBoard.iterator();
        this.numRow = rowNum;
        this.numCol = colNum;
        for (int row = 0; row != rowNum; row++) {
            for (int col = 0; col != colNum; col++) {
                this.tiles[row][col] = iterator.next();
            }
        }
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public SlidingTile getTile(int row, int col) {
        return this.tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        SlidingTile temp = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = temp;

    }

    /**
     * Getter for tiles
     */

    public SlidingTile[][] getTiles() {
        return this.tiles;
    }

    /**
     * Setter for tiles
     */

    public void setTiles(SlidingTile[][] newTiles) {
        this.tiles = newTiles;
    }

    /**
     * Replace the current SlidingBoard with a New SlidingBoard
     *
     * @param newSlidingBoard the new board to be replaced
     */

    void replaceBoard(SlidingBoard newSlidingBoard) {
        SlidingTile[][] newTiles = newSlidingBoard.getTiles();
        this.setTiles(newTiles);

    }

    /**
     * Return a copy of this SlidingBoard
     *
     * @return copy of this
     */

    SlidingBoard copy() {
        List<SlidingTile> newTiles = new ArrayList<>();
        for (SlidingTile t : this) {
            newTiles.add(t);
        }
        return new SlidingBoard(newTiles, super.getNumRow(), super.getNumCol());
    }

    @Override
    public String toString() {
        return "SlidingBoard{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * Returns an iterator for this board.
     *
     * @return an iterator for this board.
     */
    @NonNull
    @Override
    public Iterator<SlidingTile> iterator() {
        return new BoardIterator();
    }

    /**
     * An Iterator for SlidingBoard tiles.
     */
    private class BoardIterator implements Iterator<SlidingTile> {

        /**
         * The row number of the next Tile to return.
         */
        int rowIndex = 0;

        /**
         * The column number of the next Tile to return.
         */
        int colIndex = 0;

        /**
         * Returns whether there is another Tile to return.
         *
         * @return whether there is another Tile to return.
         */
        @Override
        public boolean hasNext() {
            return (rowIndex != numRow);
        }

        /**
         * Returns the next Tile.
         *
         * @return the next Tile.
         */
        @Override
        public SlidingTile next() {
            SlidingTile result = tiles[rowIndex][colIndex];
            if (rowIndex != numRow) {
                if (colIndex != numCol - 1) {
                    colIndex++;
                } else {
                    colIndex = 0;
                    rowIndex++;
                }
            }
            return result;
        }
    }
}