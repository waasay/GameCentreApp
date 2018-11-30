package group_0548.gamecentre.twentygame;

import group_0548.gamecentre.AbstractTile;
import group_0548.gamecentre.R;

/**
 * A tile representing a tile in the 2048 board
 */
public class TwentyTile extends AbstractTile {

    /**
     * The integer representing the background image of this tile.
     */
    private int background;

    /**
     * Create a new TwentyTile with id which corresponds to the number to be displayed according
     * to the relationship 2^(id+1) up to where 0 <= id <= 10 for numbered tiles and id = 11 for
     * background tile.
     * @param id the id to determine which tile to create.
     */
    TwentyTile(int id) {

        this.id = id;

        int[] images = {R.drawable.t2, R.drawable.t4, R.drawable.t8, R.drawable.t16,
                R.drawable.t32, R.drawable.t64, R.drawable.t128, R.drawable.t256, R.drawable.t512,
                R.drawable.t1024, R.drawable.t2048, R.drawable.background};

        this.background = images[id];

    }

    /**
     * Get the id of this tile.
     *
     * @return the id of this tile.
     */
    public int getId() {
        return super.getId();
    }

    /**
     * Get the background representing the background image of this tile.
     * @return the background representing the background image of this tile.
     */
    public int getBackground() {
        return background;
    }

}

