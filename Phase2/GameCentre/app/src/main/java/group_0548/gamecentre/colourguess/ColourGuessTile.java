package group_0548.gamecentre.colourguess;

import group_0548.gamecentre.AbstractTile;
import group_0548.gamecentre.R;


/**
 * A ColourGuessTile
 */
public class ColourGuessTile extends AbstractTile {

    /**
     * The background of the ColourGuessTile
     */
    private int background;

    /**
     * Constructor of the ColourGuessTile
     *
     * @param id the id of the ColourGuessTile
     */
    ColourGuessTile(int id) {

        this.id = id;

        int[] images = {R.drawable.p, R.drawable.b, R.drawable.g, R.drawable.y,
                R.drawable.o, R.drawable.r, R.drawable.w, R.drawable.t};

        this.background = images[id];

    }

    /**
     * Getter of the id ColourGuessTile
     *
     * @return the id of the ColourGuessTile
     */
    public int getId() {
        return super.getId();
    }

    /**
     * Getter of the background of the ColourGuessTile
     *
     * @return the background of the ColourGuessTile
     */
    public int getBackground() {
        return background;
    }

}
