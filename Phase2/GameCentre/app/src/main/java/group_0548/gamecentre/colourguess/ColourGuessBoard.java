package group_0548.gamecentre.colourguess;

import android.support.annotation.NonNull;

import java.util.Iterator;
import java.util.List;

import group_0548.gamecentre.AbstractBoard;

public class ColourGuessBoard extends AbstractBoard implements Iterable<ColourGuessTile> {

    private int numRow;

    private int numCol;

    private ColourGuessTile[][] tiles;


    public ColourGuessBoard(List<ColourGuessTile> tilesList, int rowNum, int colNum) {
        tiles = new ColourGuessTile[rowNum][colNum];
        Iterator<ColourGuessTile> iterator = tilesList.iterator();
        this.numRow = rowNum;
        this.numCol = colNum;
        for (int row = 0; row != rowNum; row++) {
            for (int col = 0; col != colNum; col++) {
                this.tiles[row][col] = iterator.next();
            }
        }
    }


    public int numTiles(){
        return this.numCol*this.numRow;
    }

    public ColourGuessTile getTile(int row, int col){
        return this.tiles[row][col];
    }

    public ColourGuessTile[][] getTiles(){
        return this.tiles;
    }

    public void setTiles(ColourGuessTile[][] newTiles){
        this.tiles = newTiles;
    }




    /**
     * Get the number of rows.
     *
     * @return the amount of rows.
     */
    public int getNumRow(){
        return this.numRow;
    }

    /**
     * Get the number of column.
     *
     * @return the amount of column.
     */
    public int getNumCol(){
        return this.numCol;
    }

    /**
     * Returns an iterator for this board.
     *
     * @return an iterator for this board.
     */
    @NonNull
    @Override
    public Iterator<ColourGuessTile> iterator() {
        return new ColourGuessBoard.BoardIterator();
    }

    /**
     * An Iterator for SlidingBoard tiles.
     */
    private class BoardIterator implements Iterator<ColourGuessTile> {

        /**
         * The row number of the next SlidingTile to return.
         */
        int rowIndex = 0;

        /**
         * The column number of the next SlidingTile to return.
         */
        int colIndex = 0;

        /**
         * Returns whether there is another SlidingTile to return.
         *
         * @return whether there is another SlidingTile to return.
         */
        @Override
        public boolean hasNext() {
            return (rowIndex != numRow);
        }

        /**
         * Returns the next SlidingTile.
         *
         * @return the next SlidingTile.
         */
        @Override
        public ColourGuessTile next() {
            ColourGuessTile result = tiles[rowIndex][colIndex];
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
