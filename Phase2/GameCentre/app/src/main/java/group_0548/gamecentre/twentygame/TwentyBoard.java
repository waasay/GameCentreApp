package group_0548.gamecentre.twentygame;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import group_0548.gamecentre.AbstractBoard;


public class TwentyBoard extends AbstractBoard<TwentyTile> implements Iterable<TwentyTile> {



    private TwentyTile[][] tiles;


    TwentyBoard(int rowNum, int colNum) {

        Random randomGen = new Random();

        // choose two random tiles from 2, 4
        TwentyTile randomTile1 = new TwentyTile(randomGen.nextInt(2));
        TwentyTile randomTile2 = new TwentyTile(randomGen.nextInt(2));

        // find two random rownum and colnum to place the 2/4 tiles, make sure they are not the same.
        int randomRow1 = randomGen.nextInt(rowNum);
        int randomRow2 = randomGen.nextInt(rowNum);
        int randomCol1 = randomGen.nextInt(colNum);
        int randomCol2 = randomGen.nextInt(colNum);

        while (randomRow1 == randomRow2 && randomCol1 == randomCol2) {
            randomRow1 = randomGen.nextInt(rowNum);
            randomRow2 = randomGen.nextInt(rowNum);
            randomCol1 = randomGen.nextInt(colNum);
            randomCol2 = randomGen.nextInt(colNum);
        }

        // fill entire board with background
        tiles = new TwentyTile[rowNum][colNum];
        this.numRow = rowNum;
        this.numCol = colNum;
        for (int row = 0; row != rowNum; row++) {
            for (int col = 0; col != colNum; col++) {
                this.tiles[row][col] = new TwentyTile(11);
            }
        }

        // Change two the random rownums and colnums with the appropriately chosen random 2/4 tiles.

        tiles[randomRow1][randomCol1] = randomTile1;
        tiles[randomRow2][randomCol2] = randomTile2;

    }

    public TwentyBoard(List<TwentyTile> tileSet, int numRow, int numCol) {
        tiles = new TwentyTile[numRow][numCol];
        Iterator<TwentyTile> iterator = tileSet.iterator();
        this.numRow = numRow;
        this.numCol = numCol;
        for (int row = 0; row != numRow; row++) {
            for (int col = 0; col != numCol; col++) {
                this.tiles[row][col] = iterator.next();
            }
        }


    }

    public TwentyTile getTile(int row, int col) {
        return this.tiles[row][col];
    }

    public TwentyTile[][] getTiles() {
        return this.tiles;
    }

    public void setTiles(TwentyTile[][] newTiles) {
        this.tiles = newTiles;
    }


    /**
     * Returns an iterator for this board.
     *
     * @return an iterator for this board.
     */
    @NonNull
    @Override
    public Iterator<TwentyTile> iterator() {
        return new TwentyBoard.BoardIterator();
    }


    TwentyBoard copy() {
        List<TwentyTile> newTiles = new ArrayList<>();
        for (TwentyTile t : this) {
            newTiles.add(t);
        }
        return new TwentyBoard(newTiles, numRow, numCol);
    }


    void replaceBoard(TwentyBoard newBoard) {
        TwentyTile[][] newTiles = newBoard.getTiles();
        this.setTiles(newTiles);
    }

    /**
     * An Iterator for TwentyTile.
     */
    private class BoardIterator implements Iterator<TwentyTile> {

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
        public TwentyTile next() {
            TwentyTile result = tiles[rowIndex][colIndex];
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
