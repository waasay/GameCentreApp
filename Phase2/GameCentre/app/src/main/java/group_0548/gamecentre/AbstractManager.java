package group_0548.gamecentre;

import java.io.Serializable;
import java.util.Observable;

public abstract class AbstractManager extends Observable implements Serializable {

    /**
     * The board being managed.
     */
    protected AbstractBoard board;



    public abstract boolean puzzleSolved();



    public void changeAndNotify(){
        setChanged();
        notifyObservers();
    }


}
