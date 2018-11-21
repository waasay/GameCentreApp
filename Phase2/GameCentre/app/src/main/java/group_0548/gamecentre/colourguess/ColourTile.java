package group_0548.gamecentre.colourguess;
import group_0548.gamecentre.R;
import group_0548.gamecentre.AbstractTile;

public class ColourTile extends AbstractTile{

    private int id;

    private int background;


    ColourTile(int id) {

        this.id = id;

        int[] images = {R.drawable.p, R.drawable.b, R.drawable.g, R.drawable.y,
                R.drawable.o, R.drawable.r, R.drawable.w, R.drawable.t};

        this.background = images[id];

    }

    public int getId(){
        return this.id;
    }

    public int getBackground() {
        return background;
    }

}
