package trains;

import rails.Rail;
import others.CollisionException;
import others.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * A mozdony fogja össze a vonatot, hiszen ő kezeli a hozzá tartozó vagonokat.
 * A mozdony csak mozogni tud, azt, hogy hogyan mozog, azt a sín (Rail) intézi.
 */
public class Locomotive extends Train {

    /** A mozdony mögé csatolt vagonok (Wagon) listája. */
    private List<Wagon> wagons = new ArrayList<Wagon>();
    /** A mozdony haladási iránya, ezt a sínektől kapja. */
    private Direction dir;
    /** VÁLTOZÁS
     *  A vonat üzenete, hogy mi történt vele legutoljára. (pl ütközött, leszállított utasokat, stb) */
    String message;

    /**
     * Konstruktor, lehelyezi a mozdonyt a kapott sínre.
     * @param where - ahova lehelyezi
     */
    public Locomotive(Rail where) {
        super(where);
        dir = dir.NEXT;
    }

    /**
     * Getter függvény, visszaadja a mozdony mögé csatolt vagonok listáját.
     * @return - a vagonok
     */
    public List<Wagon> getWagons() {
        return wagons;
    }

    /**
     * Hozzáad egy vagont a mozdony vagonjainak listájára (végére)
     * @param wagon - a vagon, amit hozzáadunk
     */
    public void addWagon(Wagon wagon) {
        wagons.add(wagon);
    }

    /**
     * Getter függvény, visszaadja a mozdony mozgásának irányát.
     * @return az irány, amerre a mozdony mozog
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Setter függvény, beállítja az irány, amerre a mozdony mozog
     * @param dir - az irány amerre a mozdony mozog
     */
    public void setDir(Direction dir) {
        this.dir = dir;
    }

    /**
     * A mozdony léptetése. Meghívja annak a sínnek a moveToNext függvényét,
     * amelyiken a vonat éppen áll.
     * @throws CollisionException
     */
    public void nextStep() throws CollisionException {
        rail.moveToNext(this);
    }

    /** VÁLTOZOTT
     *  Setter függvény, beállítja a vonat által küldött "üzenetet".
     *  @param message - mi történik az adott vonattal
     */
    public void setMessage(String message){
        this.message = message;
    }

    /** VÁLTOZOTT
     *  Getter függvény, visszaadja a vonat által küldött "üzenetet".
     *  @return mi történik az adott vonattal
     */
    public String getMessage(){
        return this.message;
    }
}
