package others;

/**
 * Saját kivétel osztály. Akkor dobódik, amikor egy vonat ütközik egy másik vonattal,
 * felvág egy váltót, vagy kimegy a pályáról. Ilyen kivételt a Rail leszármazottjai dobnak.
 * A Stageben van feldolgozva az összes ilyen kivétel.
 */
public class CollisionException extends Exception {

    /**
     * Létrehozza a kivételt a paraméterben kapott stringgel..
     * @param message - a kivétel üzenete
     */
    public CollisionException(String message){
        super(message);
    }
}
