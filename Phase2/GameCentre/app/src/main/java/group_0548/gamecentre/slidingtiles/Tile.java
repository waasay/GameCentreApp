package group_0548.gamecentre.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

import group_0548.gamecentre.AbstractTile;
import group_0548.gamecentre.R;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile extends AbstractTile implements Comparable<Tile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private int background;

    /**
     * The unique id.
     */
    private int id;

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId the id of the tile background
     */
    Tile(int backgroundId, int rowNum, int colNum) {
        id = backgroundId + 1;

        int[] images = {R.drawable.tile_1, R.drawable.tile_2, R.drawable.tile_3, R.drawable.tile_4,
                R.drawable.tile_5, R.drawable.tile_6, R.drawable.tile_7, R.drawable.tile_8,
                R.drawable.tile_9, R.drawable.tile_10, R.drawable.tile_11, R.drawable.tile_12,
                R.drawable.tile_13, R.drawable.tile_14, R.drawable.tile_15, R.drawable.tile_16,
                R.drawable.tile_17, R.drawable.tile_18, R.drawable.tile_19,
                R.drawable.tile_20, R.drawable.tile_21, R.drawable.tile_22,
                R.drawable.tile_23, R.drawable.tile_24, R.drawable.tile_25};

        if (id == rowNum * colNum) {
            background = images[images.length - 1];
        } else {
            for (int i = 1; i < rowNum * colNum; i++) {
                if (i == id) {
                    background = images[i - 1];
                    break;
                }
            }
        }
    }

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
