package group_0548.gamecentre;

import java.io.Serializable;

/**
 * Abstract SlidingTile Object for the individual tile object in the game.
 */
public abstract class AbstractTile implements Serializable {

    /**
     * An unique ID for the this abstract tile
     */
    protected int id;

    /**
     * Getter for id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Abstract method for getting the background of tile, since the background
     * is intrinsically linked to the game, it is implemented as an abstract method.
     *
     * @return the background
     */
    public abstract int getBackground();


}
