package rails;

import others.CollisionException;
import others.Direction;
import trains.Locomotive;
import trains.Wagon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Minden sínnek az ősosztálya. Ez az osztály biztosítja, hogy vonatot (Train) lehessen rá helyezni,
 * valamint biztosít gettert, settert a fontosabb attribútumokhoz.
 * Az osztály absztrakt, hiszen néhány függvénye sínspecifikus.
 * Tárolja továbbá, hogy van-e rajta mozdony (Locomotive), a váltó (SwtichRail) checkNeighbors() függvénye miatt.
 */
public abstract class Rail implements Serializable {

    /** A következő sín. */
    protected Rail next;
    /** Az előző sín. */
    protected Rail previous;
    /** True, ha tartózkodik az adott időpillanatban a sínen mozdony, false ha nem. */
    protected boolean onrail;
    /** Változott
     *  Szükség volt rá, hogy a cross-railnél tudjuk vizsgálni az ütközést
     *  True, ha tartózkodik az adott időpillanatban a sínen vagon, false ha nem. */
    protected Wagon onrailWagon;
    protected boolean hasTunnel = false;

    /**
     * Konstruktor.
     */
    public Rail () {}

    /**
     * Getter függvény, visszaadja a következő sínt.
     * @return következő sín
     */
    public Rail getNext () {
        return next;
    }

    /**
     * Getter függvény, visszaadja az előző sínt.
     * @return előző sín
     */
    public Rail getPrevious () {
        return previous;
    }

    /**
     * Setter függvény, beállítja az előző, és a következő sínt.
     * @param previous - előző sín
     * @param next - következő sín
     */
    public void set(Rail previous, Rail next) {
        this.next = next;
        this.previous = previous;
    }

    /**
     * Setter függvény, beállítja az előző sínt.
     * @param previous - előző sín
     */
    public void setPrevious (Rail previous) {
        this.previous = previous;
    }

    /**
     * Setter függvény, beállítja a következő sínt.
     * @param next - következő sín
     */
    public void setNext (Rail next) {
        this.next = next;
    }

    /**
     * Getter függvény, visszaadja, hogy tartózkodik-e mozdony a sínen.
     * @return true, ha van, false ha nincs
     */
    public boolean getLocomotive () {
        return onrail;
    }

    public Wagon getWagon() {
        return onrailWagon;
    }

    /**
     * Az aktuális sínen álló mozdony a következő sínre mozgatása. Absztrakt függvény,
     * a származtatott osztályokban van definiálva a pontos működése.
     * @param loco - a mozdony, ami a sínen áll
     * @throws CollisionException - Ütközés esetén dobja.
     */
    public abstract void moveToNext(Locomotive loco) throws CollisionException;

    /**
     * Megmondja az az adott sínről, hogy helyezhető-e rá alagút. Absztrakt függvény,
     * a származtatott osztályokban dől el, hogy igen, vagy nem.
     * @return
     */
    public abstract boolean canPlaceTunnel();

    /**
     * Setter függvény, ami az adott sínre ráhelyez egy mozdonyt.
     * @param onrail - a mozdony
     */
    public void setLocomotive (boolean onrail) {
        this.onrail = onrail;
    }

    /**
     * Ütközés detektálás. Miután megtörtént a lépés, megnézi, hogy ahova lépett,
     * ott van-e mozdony, vagy vagon.
     * @param loco - a
     * @throws CollisionException - Akkor dobja, ha "ütközés" van.
     */
    protected void collisionDetection(Locomotive loco) throws CollisionException {
        if (loco.getRail().onrail || loco.getRail().onrailWagon != null) {
            loco.setMessage(" crashed with other train");
            throw new CollisionException(" crashed with other train.");
            //return false;
        }
    }

    /**
     * Mozdonyok léptetése.
     * @param loco - léptetendő mozdony
     * @throws CollisionException - Akkor dobja, ha "ütközés" van.
     */
    protected void moveLocomotive(Locomotive loco) throws CollisionException {
        // Ha az iránya next, lekérjük a next railt
        if (loco.getDir() == Direction.NEXT) {

            if (loco.getRail().getNext() == null) {
                loco.setMessage(" crashed on map edge");
                throw new CollisionException(" crashed on map edge");
            }
            loco.setRail(loco.getRail().getNext());
        }
        // Ha az iránya previous, akkor lekérjük a previous railt
        else {
            // Ha nincs previous rail, akkor kiment a pályáról
            if (loco.getRail().getPrevious() == null) {
                loco.setMessage(" crashed on map edge");
                throw new CollisionException(" crashed on map edge");
            }
            loco.setRail(loco.getRail().getPrevious());
        }
    }

    /**
     * Vagonok léptetése.
     * @param loco - léptetendő vonat (erre a mozdonyra vannak felfűzve a vagonok)
     */
    protected void moveWagons(Locomotive loco) {
        List<Wagon> wagons = loco.getWagons();

        // Ha vannak vagonok, akkor először az elsőt léptetjük a mozdony után
        if (wagons.size() > 0) {
            // Levesszük az aktuálisról
            wagons.get(0).getRail().onrailWagon = null;
            // Léptetjük
            if (wagons.get(0).getRail().getNext().getNext().equals(loco.getRail()))
                wagons.get(0).setRail(wagons.get(0).getRail().getNext());
            else if (wagons.get(0).getRail().getPrevious().getPrevious().equals(loco.getRail()))
                wagons.get(0).setRail(wagons.get(0).getRail().getPrevious());
            else if (wagons.get(0).getRail().getNext().getPrevious().equals(loco.getRail()))
                wagons.get(0).setRail(wagons.get(0).getRail().getNext());
            // Rárakjuk az aktuálisra
            wagons.get(0).getRail().onrailWagon = wagons.get(0);

            // És ha nem csak egy van, akkor a többit is
            for (int i = 1; i < wagons.size(); i++) {
                // Levesszük az aktuálisról
                wagons.get(i).getRail().onrailWagon = null;
                // Léptetjük
                if (wagons.get(i).getRail().getNext().getNext().equals(wagons.get(i-1).getRail()))
                    wagons.get(i).setRail(wagons.get(i).getRail().getNext());
                else if (wagons.get(i).getRail().getPrevious().getPrevious().equals(wagons.get(i-1).getRail()))
                    wagons.get(i).setRail(wagons.get(i).getRail().getPrevious());
                else if (wagons.get(i).getRail().getNext().getPrevious().equals(wagons.get(i-1).getRail()))
                    wagons.get(i).setRail(wagons.get(i).getRail().getNext());
                // Rárakjuk az aktuálisra
                wagons.get(i).getRail().onrailWagon = wagons.get(i);
            }
        }
    }
    public boolean getTunnel(){
        return hasTunnel;
    }
    public void setTunnel(boolean tunnel){
        hasTunnel = tunnel;
    }
}
