package group_0548.gamecentre.twentygame;

import group_0548.gamecentre.AbstractTile;
import group_0548.gamecentre.R;

public class TwentyTile extends AbstractTile {

    private int background;

    TwentyTile(int id) {

        this.id = id;

        int[] images = {R.drawable.t2, R.drawable.t4, R.drawable.t8, R.drawable.t16,
                R.drawable.t32, R.drawable.t64, R.drawable.t128, R.drawable.t256, R.drawable.t512,
                R.drawable.t1024, R.drawable.t2048, R.drawable.background};

        this.background = images[id];

    }

    public int getId(){
        return super.getId();
    }

    public int getBackground() {
        return background;
    }

}

