package rails;

import others.CollisionException;
import others.Color;
import trains.Locomotive;
import trains.Wagon;

import java.util.List;

/**
 * Az állomások (StationRail), ahol a vagonokról (Wagon) le- illetve felszálhatnak utasok.
 * Leszállás akkor történhet meg, ha az első nemüres vagon színe megegyezik az állomás színével.
 * Felszállás akkor történhet meg, ha nem történt leszállás, valamint a vagon színe megegyezik az állomás színével.
 * Ezen kívül lépteti a vonatot, mint a többi sín.
 * Egy állomás lehet nyitva, vagy zárva, ha zárva van, nem lehet kiüríteni a vagonokat.
 */
public class StationRail extends Rail{

    /** Az állomás színe. */
    private boolean open;
    /** Az állomás állapota (nyitott, zárt). */
    private Color color;

    /**
     * Konstruktor, ami beállítja az állomás színét, és hogy használatban van-e.
     * @param color - állomás színe
     * @param open - állomás használhatósága
     */
    public StationRail (Color color, boolean open) {
        super();
        this.color = color;
        this.open = open;
    }

    /**
     * Getter függvény, visszaadja, hogy nyitva van-e az állomás.
     * @return true, ha nyitva van, false, ha nincs nyitva
     */
    public boolean getUsable () {
        return open;
    }

    /**
     * Setter függvény, beállítja az állomás színét.
     * @param color - a beállítandó szín
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Setter függvény, beállítja, hogy az állomás nyitva van-e.
     * @param usable - true, ha nyitva van, false, ha nem
     */
    public void setUsable (boolean usable) {
        this.open = usable;
    }

    /**
     * Getter függvény, visszaadja az állomás színét.
     * @return az állomás színe
     */
    public Color getColor () {
        return color;
    }

    /**
     * Az utasok leszállítása illetve felszállítása a vagonokról.
     * @param loco - mozdony, aminek a vagonjairól leszállnak/felszállnak az utasok
     */
    protected void passengerDelivery(Locomotive loco) {
        List<Wagon> wagons = loco.getWagons();
        for (int i=0; i<wagons.size(); i++) {
            // Szenesvagonon nincs utas
            if(wagons.get(i).getColor().equals(Color.BLACK))
                continue;
            // Ha nem egyezett meg a színe, és nem volt üres, nem száll le senki
            if (!wagons.get(i).isEmpty() && !wagons.get(i).getColor().equals(this.color))
                break;
            // Ha megegyezett a színe, és nem üres. akkor leszállnak az utasok
            else if (!wagons.get(i).isEmpty() && wagons.get(i).getColor().equals((this.getColor()))) {
                wagons.get(i).empty();
                loco.setMessage(" " + wagons.get(i).getColor().toString().toLowerCase() + " passengers got off");
                return;
            }
        }
        // Ha ide jutottunk, nem szállt le utas
        // Úgyhogy felszállítunk utast, ha tudunk
        for (int i=0; i<wagons.size(); i++){
            if(wagons.get(i).isEmpty() && wagons.get(i).getColor().equals(this.color)){
                wagons.get(i).fill();
                loco.setMessage(" " + wagons.get(i).getColor().toString().toLowerCase() + " passengers got on");
                return;
            }
        }
    }

    /**
     * Az aktuális sínen álló mozdony a következő sínre mozgatása.
     * @param loco - a mozdony, ami a sínen áll
     * @throws CollisionException - Ütközés esetén dobja.
     */
    @Override
    public void moveToNext(Locomotive loco) throws CollisionException {
        passengerDelivery(loco);
        moveLocomotive(loco);
        collisionDetection(loco);
        loco.getRail().setLocomotive(true);
        this.onrail = false;
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
