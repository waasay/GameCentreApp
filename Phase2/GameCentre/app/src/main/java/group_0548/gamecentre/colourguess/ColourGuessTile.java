package group_0548.gamecentre.colourguess;

import group_0548.gamecentre.AbstractTile;
import group_0548.gamecentre.R;

public class ColourGuessTile extends AbstractTile {

    private int background;


    ColourGuessTile(int id) {

        super.id = id;

        int[] images = {R.drawable.p, R.drawable.b, R.drawable.g, R.drawable.y,
                R.drawable.o, R.drawable.r, R.drawable.w, R.drawable.t};

        this.background = images[id];

    }

    public int getId(){
        return super.getId();
    }

    public int getBackground() {
        return background;
    }

}
