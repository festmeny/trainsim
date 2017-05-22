package rails;

import others.CollisionException;
import trains.Locomotive;

import java.io.Serializable;

/**
 * A sínnek a legáltalánosabb leszármazottja. A dolga csak a mozdony következő sínre való mozgatása.
 * Ehhez overridolja az örökölt moveToNext() függvényt.
 * Ezen kívül csak erre a sínre lehet építeni alagutat (Tunnel).
 */
public class NormalRail extends Rail{

    /**
     * Az aktuális sínen álló mozdony a következő sínre mozgatása.
     * @params loco - a mozdony, ami a sínen áll
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
     * @return true, mivel helyezhető rá alagút
     */
    @Override
    public boolean canPlaceTunnel() {
        return true;
    }
}
