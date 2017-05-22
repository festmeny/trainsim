package trains;

import others.Color;
import rails.Rail;

/**
 * A vagonokban (Wagon) utasok, vagy szén helyezkednek el.
 * Az utasokat kell eljuttatni az állomásokra (StationRail).
 * A játékszabályok szerint lehet kiüríteni, feltölteni a vagonokat.
 */
public class Wagon extends Train{

    /** A vagon színe. */
    private Color color;
    /** Van-e a vagonban utas. */
    private boolean isEmpty;

    /**
     * Konstruktor, beállítja a vagon pozícióját, illetve színét.
     * @param where - a sín, ahova lehelyezi
     * @param color - a vagon színe
     */
    public Wagon (Rail where , Color color) {
        super(where);
        this.color = color;
        isEmpty = false;
    }

    /**
     * Getter függvény, visszaadja a vagon színét.
     * @return a vagon színe
     */
    public Color getColor() {
        return color;
    }

    /**
     * Getter függvény, visszaadja, hogy vannak-e a vonaton utasok.
     * @return true, ha vannak, false, ha nincsenek
     */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
     * Kiüríti a vagont.
     */
    public void empty () {
        isEmpty = true;
    }

    /**
     * Feltölti a vagont.
     */
    public void fill(){
        isEmpty = false;
    }
}
