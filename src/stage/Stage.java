package stage;

import game.GameOverMenu;
import others.CollisionException;
import others.Color;
import rails.*;
import trains.CoalWagon;
import trains.Locomotive;
import trains.Wagon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.Timer;

/**
 * Felépíti a pályát, lehetőséget biztosít a szimulálásra a metódusai segítségével.
 * Valamint a pálya játékos általi manipulálása ebben a classban van megvalósítva.
 */
public class Stage implements Serializable {

    /** A pályán lévő vonatok. */
    private List<Locomotive> locomotives = new ArrayList<Locomotive>();
    /** A pályán lévő állomások. */
    private List<StationRail> stations = new ArrayList<StationRail>();
    /** A pályán lévő sínek. */
    public List<Rail> rails =  new ArrayList<Rail>();
    /** A pályán lévő váltók. */
    private List<SwitchRail> switches =  new ArrayList<SwitchRail>();
    /** A pályán lévő kereszt irányú sínek. */
    private List<CrossRail> crosses =  new ArrayList<CrossRail>();
    /** A pályán lévő alagút. */
    private Tunnel tunnel = new Tunnel();
    /** VÁLTOZTATÁS
     *  Ide generáljuk a vagonokat. (Könnyebb így tárolni) */
    private Rail startrail;
    /** VÁLTOZTATÁS
     *  A bemenet hossza. Ilyen hosszú vonatot lehet generálni max */
    private final int inputLength = 4;
    /** VÁLTOZTATÁS
     *  Megnyerte-e a játékot. */
    private boolean won = false;

    private Draw draw;

    /** VÁLTOZTATÁS
     * Getter függvény, visszaadja, hogy a játékos megnyerte-e a pályát.
     * @return true, ha igen, false, ha nem
     */
    public boolean getWon(){
        return won;
    }

    javax.swing.Timer timer = new javax.swing.Timer(500, new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!tick()){
                draw.dispose();
                GameOverMenu menu = new GameOverMenu();
                if (won)
                    menu.gameOverLabel.setText("You win!");
                else
                    menu.gameOverLabel.setText("You lose!");
                timer.stop();
            }
        }
    });

    /**
     * Ez a függvény generál színeket a vagonoknak. Csak olyan színt generálhat,
     * amilyen színű állomások aktívak. Ezt tesztnél nem használjuk, mert random generálja a színeket.
     * @return a generált szín
     */
    private Color generateWagonColor () {
        // Legenerálja a színt, majd visszatér vele
        int index = new Random().nextInt(6);
        switch (index){
            case 0:
                return Color.RED;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.ORANGE;
            case 5:
                return Color.BLACK;
            default:
                return null;
        }
    }


    public boolean generateTrain (int length) {
        Locomotive newLocomotive = new Locomotive(startrail);
        Color generatedColor, previousColor = null;
        if (length > 0) {
            //Generálunk egy új színt
            generatedColor = generateWagonColor();
            //Megnézzük, hogy megegyezik az előzővel. Amíg megegyezik, újat generálunk.
            while (generatedColor.equals(previousColor)) {
                generatedColor = generateWagonColor();
            }
            //Berakjuk a mozdony mögötti sínre, ha van olyan
            if (startrail.getPrevious() != null) {
                if (generatedColor == Color.BLACK)
                    newLocomotive.addWagon(new CoalWagon(startrail.getPrevious()));
                else
                    newLocomotive.addWagon(new Wagon(startrail.getPrevious(), generatedColor));;
            }else
                return false;
            //Elmentjük ezt az a színt az előzőnek.
            previousColor = generatedColor;
            //Ugyanezt megcsináljuk a többi vagonra is, ha több kell mint 1
            for (int i = 1; i < length; i++) {
                generatedColor = generateWagonColor();
                while (generatedColor.equals(previousColor)) {
                    generatedColor = generateWagonColor();
                }
                //Berakjuk az utolsó vagon mögötti sínre, ha van olyan
                if (newLocomotive.getWagons().get(i-1).getRail().getPrevious() != null)
                    newLocomotive.addWagon(new Wagon(newLocomotive.getWagons().get(i-1).getRail().getPrevious(), generatedColor));
                else
                    return false;
                previousColor = generatedColor;
            }
        }
        locomotives.add(newLocomotive);
        return true;
    }




    /**
     * A pálya felépítése.
     * @param - melyik típusú pályát hozza létre. Station esetén az állomások színei is.
     */
    public void init(){
        for (int i = 0; i < stations.size(); i++) {
            Color color = generateWagonColor();
            while (color == Color.BLACK)
                color = generateWagonColor();
            stations.get(i).setColor(color);
        }

        /** Ez egy pálya létrehozása. Nincs befejezve! */
/*
        //Létrehozzuk a síneket
        rails.add(new NormalRail());
        rails.add(new NormalRail());
        rails.add(new NormalRail());
        rails.add(new NormalRail());
        rails.add(new NormalRail());

        rails.add(new NormalRail());                                //5

        rails.add(new NormalRail());                                //6
        rails.add(new NormalRail());                                //7
        rails.add(new SwitchRail());                                //8
        rails.add(new StationRail(Color.YELLOW, true));       //9
        rails.add(new NormalRail());                                //10
        rails.add(new SwitchRail());                                //11

        rails.add(new StationRail(Color.YELLOW, true));       //12
        rails.add(new NormalRail());                                //13
        rails.add(new StationRail(Color.YELLOW, true));       //14

        rails.add(new SwitchRail());                                //15
        rails.add(new NormalRail());                                //16
        rails.add(new CrossRail());                                 //17
        rails.add(new NormalRail());                                //18
        rails.add(new NormalRail());                                //19
        rails.add(new SwitchRail());                                //20

        rails.add(new NormalRail());                                //21
        rails.add(new NormalRail());                                //22
        rails.add(new NormalRail());                                //23

        rails.add(new StationRail(Color.YELLOW, true));       //24
        rails.add(new NormalRail());                                //25
        rails.add(new SwitchRail());                                //26
        rails.add(new StationRail(Color.YELLOW, true));       //27
        rails.add(new NormalRail());                                //28
        rails.add(new NormalRail());                                //29

        //Felvesszük a létrehozott sínek közül az összes váltót a váltó listába
        for (Rail rail : rails)
            if (rail instanceof SwitchRail)
                switches.add((SwitchRail)(rail));

        //Felvesszük a létrehozott sínek közül az összes állomást az állomás listába
        for (Rail rail : rails)
            if (rail instanceof StationRail)
                stations.add((StationRail)(rail));

        //Felvesszük a létrehozott sínek közül az összes keresztsínt az keresztsín listába
        for (Rail rail : rails)
            if(rail instanceof CrossRail)
                crosses.add((CrossRail)rail);

        //Első sín előzője null
        rails.get(0).set(null, rails.get(1));

        //Az inputot bekötjük
        for (int i=1; i<=4; i++)
            rails.get(i).set(rails.get(i-1), rails.get(i+1));

        //Bejárat csak kívülről elérhető, belülről kifele null
        rails.get(5).set(null,rails.get(11));

        rails.get(6).set(rails.get(7),rails.get(12));
        rails.get(7).set(rails.get(8), rails.get(6));
        rails.get(8).set(rails.get(9), rails.get(7));
        switches.get(0).setOther(rails.get(13), rails.get(7));
        rails.get(9).set();

        //Az input rail a 4-es
        startrail = rails.get(4);



        //Az összes váltót egyszer kell váltani hogy működőképesek legyenek,
        //úgyhogy, hogy alaphelyzetbe kerüljenek kétszer le kell váltani az összes váltót
        for (SwitchRail iSwitch : switches) {
            iSwitch.setSwitch();
            iSwitch.setSwitch();
        }

        draw.map[2][0] = rails.get(1+inputLength);
        draw.map[2][1] = rails.get(2+inputLength);
        draw.map[2][2] = rails.get(3+inputLength);
        draw.map[2][3] = rails.get(4+inputLength);
        draw.map[2][4] = rails.get(5+inputLength);
        draw.map[2][5] = rails.get(6+inputLength);
*/
    }

    /**
     * Az összes Stage-hez tartozó Locomotive-ot lépteti, valamint az összes váltó felméri,
     * hogy a környezetében van-e Locomotive, és aszerint állítja be a saját Direction enumját.
     * @return true, ha nem történt hiba, false, ha ütközés történt, vagy a játékos megnyerte a pályát
     */
    public boolean tick () {
        //Vonatok léptetése
        try {
            for (int locoNumber = 0; locoNumber < locomotives.size(); locoNumber++) {
                for (CrossRail iCross : crosses)
                    iCross.checkNeighbors(locomotives.get(locoNumber));;
                locomotives.get(locoNumber).nextStep();
            }
        }
        // Ha ütközés történt
        catch (CollisionException e) {
            return false;
        }

        // Megvizsgálja, hogy minden utas leszállt-e már
        outerloop:
        for (Locomotive loco : locomotives){
            for (Wagon wagon : loco.getWagons())
                if (!wagon.getColor().equals(Color.BLACK))
                    if(!wagon.isEmpty())
                        // Ha van még utas, mindkét ciklusból ki kell lépnie
                        break outerloop;
            won = true;
            // Itt is ki kell íni, mert visszatérünk utána
            return false;
        }

        // Váltók felmérik a szomszédos pozícióikat
        for (SwitchRail iSwitch : switches)
            iSwitch.checkNeighbors();
        draw.drawToDisplay();
        return true;
    }

    /**
     * A paraméterben kapott sínt manipulálja.
     * @param  - a manipulálandó sín sorszáma
     * @return true, ha sikeres volt a manipuláció, false, ha sikertelen
     */
    public boolean modifyTiles(int x, int y) {
        if (draw.map[y][x] == null)
            return false;
        // Amennyiben ez a sín váltó volt, akkor átváltja a másik állásba.
        for (SwitchRail rail : switches) {
            if (draw.map[y][x].equals(rail)) {
                return rail.setSwitch();
            }
        }

        // Amennyiben nem váltó volt, lekérdezi, hogy lehet-e alagutat helyezni rá.
        if (draw.map[y][x].canPlaceTunnel()) {
            // Ha lehet rá alagutat helyezni, meghívja a modifyTunnel függvényt, ami elvégzi a szükséges
            // manipulációkat az adott sínen
            tunnel.modifyTunnel(draw.map[y][x]);
            return true;
        }
        // Olyan sínt válaszott, ahova nem lehet alagutat helyezni (nem sikerült manipulálni)
        return false;
    }

    public class ClickHandler extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            int x = new Double((e.getX()-3) /100).intValue();
            int y = new Double((e.getY()-26) /100).intValue();
            modifyTiles(x,y);
            draw.pane.repaint();
        }
    }

    public void save(String where){
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(where);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(rails);
            out.writeObject(stations);
            out.writeObject(switches);
            out.writeObject(crosses);
            out.writeObject(startrail);
            out.writeObject(draw.map);
            out.close();
            fileOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void load(String where){
        try{
            FileInputStream fileIn = new FileInputStream(where);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            rails = (List<Rail>) in.readObject();
            stations = (List<StationRail>) in.readObject();
            switches = (List<SwitchRail>) in.readObject();
            crosses = (List<CrossRail>) in.readObject();
            startrail = (Rail) in.readObject();
            draw = new Draw();
            draw.map = (Rail[][]) in.readObject();
            draw.addMouseListener(new ClickHandler());
            draw.pane.repaint();
            in.close();
            fileIn.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void startSimulation(){
        timer.start();
    }
}
