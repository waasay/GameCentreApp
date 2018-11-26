package group_0548.gamecentre;

import java.io.Serializable;
import java.util.Observable;

public abstract class AbstractManager<T> extends Observable implements Serializable {


    public abstract boolean puzzleSolved();


    public void changeAndNotify() {
        setChanged();
        notifyObservers();
    }


}
