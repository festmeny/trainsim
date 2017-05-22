package rails;

import others.CollisionException;
import others.Direction;
import trains.Locomotive;

/**
 * A váltó (SwitchRail) tárol egy Direction enumot, melyet a szomszédjaiban lévő
 * mozdonyok (Locomotive) állásából dönt el. Ezt a Directiont adja át a rálépő mozdonynak.
 * A mozdony eszerint a Direction szerint fog lépni a következő váltóig.
 * A váltó továbbá nem kettő sínt köt össze, mint a többi sín, hanem hármat.
 * Van egy főág-beli sín, valamint kettő mellékág-beli sín.
 * A mellékág-beli sínek közül lehet a váltás funkció segítségével kiválasztani, hogy melyik legyen aktív.
 */
public class SwitchRail extends Rail{

    /** A váltó aktív állása, amerre a vonatok tovább tudnak közlekedni. */
    private Rail active;
    /** A váltó másik állása, az adott időpillanatban ebbe az irányba nem tudnak továbbhaladni,
     * illetve az erről érkező vonatok kisiklanak. */
    private Rail othernext;
    /** A mozgási irány amit a sín átad a rajta levő mozdonynak. */
    private Direction dir;

    /**
     * Beállítja a váltó másik állását.
     * @param othernext - a váltó másik állása
     * @param active - a váltó aktív állása
     */
    public void setOther(Rail othernext, Rail active) {
        this.othernext = othernext;
        this.active = active;
    }

    /**
     * A váltó átállítása.
     * @return true, ha sikerült átállítani, false
     */
    public boolean setSwitch () {
        if (onrail == false && onrailWagon == null) {

            if (active.equals(next)) {
                active = othernext;

                if (othernext.getNext() == null)
                    othernext.setNext(this);
                else if (othernext.getPrevious() == null)
                    othernext.setPrevious(this);

                if (next.getNext().equals(this))
                    next.setNext(null);
                else if (next.getPrevious().equals(this))
                    next.setPrevious(null);

                checkNeighbors();
                return true;
            }
            else if (active.equals(othernext)) {
                active = next;

                if (next.getNext() == null)
                    next.setNext(this);
                else if (next.getPrevious() == null)
                    next.setPrevious(this);

                if (othernext.getNext().equals(this))
                    othernext.setNext(null);
                else if (othernext.getPrevious().equals(this))
                    othernext.setPrevious(null);

                checkNeighbors();
                return true;
            }
            else {
                System.out.println("Nagy baj van!");
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Megnézi, hogy a szomszéd síneken tartózkodik-e mozdony.
     */
    public void checkNeighbors() {
        if (previous.getLocomotive()){
            if (active.previous.equals(this)) {
                dir = dir.NEXT;
            }
            else if (active.next.equals(this)) {
                dir = dir.PREVIOUS;
            }
        }
        else if (next.getLocomotive())
            dir = dir.PREPRE;
        else if (othernext.getLocomotive())
            dir = dir.PREPRE;
    }

    /**
     * Getter függvény, visszaadja a következő sínt. Overrideolva van az őshöz képest,
     * mert váltó esetében a next kétféle lehet. Ez az aktívot adja vissza.
     * @return a váltó pillanatnyi következő sínje
     */
    @Override
    public Rail getNext () {
        return active;
    }

    /**
     * Mozdonyok léptetése.
     * @param loco - léptetendő mozdony
     * @throws CollisionException - Akkor dobja, ha "ütközés" van.
     */
    @Override
    protected void moveLocomotive(Locomotive loco) throws CollisionException {
        //Valamelyik mellékágból jön a főágra, úgyhogy biztos, hogy a getPre-t kell meghívni
        if (dir == dir.PREPRE) {
            loco.setRail(this.previous);
        }
        //A főágról jön, itt egyet biztosan lép előre (az aktívra).
        else {
            loco.setRail(this.active);
        }
    }

    /**
     * Az aktuális sínen álló mozdony a következő sínre mozgatása.
     * @param loco - a mozdony, ami a sínen áll
     * @throws CollisionException - Ütközés esetén dobja.
     */
    @Override
    public void moveToNext(Locomotive loco) throws CollisionException {
        //A mozdony irányának beállítása
        loco.setDir(dir);
        //Mozdony léptetése
        moveLocomotive(loco);
        //Ütközés detektálás
        collisionDetection(loco);
        //Sínre ráhelyezés
        loco.getRail().setLocomotive(true);
        //Előző sínről levétel
        this.onrail = false;
        //Vagonok léptetése
        moveWagons(loco);
    }

    /**
     * Megmondja az adott sínről, hogy helyezhető-e rá alagút.
     * @return false, mivel nem helyezhető rá alagút
     */
    @Override
    public boolean canPlaceTunnel() {
        return false;
    }
}
