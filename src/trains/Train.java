package trains;

import rails.Rail;

import java.io.Serializable;

/**
 * A sínen járó eszközöket megvalósító absztrakt osztály.
 */
public abstract class Train implements Serializable {

    /** A sínen járó eszköz tartózkodási helye. */
    protected Rail rail;

    /**
     * Konstruktor, lehelyezi a vonatot a kapott sínre.
     * @param where - ahova lehelyezi
     */
    public Train(Rail where) {
        rail = where;
    }

    /**
     * Getter függvény, visszaadja a sínt, amelyiken áll.
     * @return ahol áll
     */
    public Rail getRail() {
        return rail;
    }

    /**
     * Setter függvény, beállítja, hogy melyik sínen álljon
     * @param rail - a sín, ahova rakjuk
     */
    public void setRail(Rail rail) {
        this.rail = rail;
    }
}
