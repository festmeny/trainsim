package trains;

import others.Color;
import rails.Rail;

/**
 * A szeneskocsi (CoalWagon), egy olyan vagon, amiben nem utazhatnak utasok,
 * és a színe fekete. A vonaton bárhol elhelyezkedhet.
 */
public class CoalWagon extends Wagon {

    /**
     * Konstruktor, beállítja a vagon pozícióját, illetve színét feketére.
     * @param where - a sín, ahova lehelyezi
     */
    public CoalWagon(Rail where) {
        super(where, Color.BLACK);
    }

    /** VÁLTOZOTT
     *  A tervvel ellentétben inkább mindig teli a szenesvagon, így logikusabb.
     *  Getter függvény, visszaadja, hogy vannak-e a vonaton utasok.
     *  @return false, mivel a szenesvagonban nincsenek utasok
     */
    @Override
    public boolean isEmpty() {
        return false;
    }
}
