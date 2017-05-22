package rails;

import others.CollisionException;
import trains.Locomotive;
import trains.Wagon;

/**
 * A keresztirányú síneken (CrossRail) a vonatok azonos szintben keresztezhetik egymás útját.
 * 2 előző és 2 következő sínje van. A dolga a mozdony következő sínre való mozgatása.
 * Ehhez overridolja az örökölt moveToNext() függvényt, úgy hogy megjegyzi,
 * hogy az adott szerelvény melyik előző sínjéről érkezett,
 * és az annak megfelelő következőre fogja tovább léptetni.
 */
public class CrossRail extends Rail{

    /** Az alternatív következő sín. */
    private Rail otherpre;
    /** Az atlternatív előző sín. */
    private Rail othernext;
    /** VÁLTOZOTT
     *  Az aktív előző sín. */
    private Rail activepre = previous;
    /** VÁLTOZOTT
     *  Az aktív következő sín. */
    private Rail activenext = next;

    /**
     * Getter függvény, visszaadja a következő sínt.
     * @return következő sín
     */
    @Override
    public Rail getNext() {
        return activenext;
    }

    /**
     * Getter függvény, visszaadja az előző sínt.
     * @return előző sín
     */
    @Override
    public Rail getPrevious(){
        return activepre;
    }

    /** VÁLTOZOTT
     *  Beállítja a kereszt irányú előző, és következő síneket.
     *  @param otherpre - a kereszt irányú sín előzője
     *  @param othernext - a kereszt irányú sín következője
     */
    public void setOther(Rail otherpre, Rail othernext){
        this.otherpre = otherpre;
        this.othernext = othernext;
    }

    /**VÁLTOZOTT
     * Elég ha csak az adott mozdonyra vizsgáljuk, nem kell az összes locoval, ezért új paramétert hoztunk be.
     * Megnézi, hogy a szomszéd síneken tartózkodik-e a paraméterben kapott mozdony.
     * @param loco - a keresett mozdony
     */
    public void checkNeighbors(Locomotive loco) {
        if (loco.getRail().equals(previous))
            activenext = next;
        if (loco.getRail().equals(otherpre))
            activenext = othernext;
        if (loco.getRail().equals(next))
            activepre = previous;
        if (loco.getRail().equals(othernext))
            activepre = otherpre;
    }

    /**
     * Az aktuális sínen álló mozdony a következő sínre mozgatása.
     * @param loco - a mozdony, ami a sínen áll
     * @throws CollisionException - Ütközés esetén dobja.
     */
    @Override
    public void moveToNext(Locomotive loco) throws CollisionException {
        //Mozdony léptetése
        moveLocomotive(loco);
        //Ütközés detektálás
        collisionDetection(loco);
        //A sínre ráhelyezés
        loco.getRail().setLocomotive(true);
        //Előző sínről levétel
        this.onrail = false;
        //Vagonok mozgatása
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
