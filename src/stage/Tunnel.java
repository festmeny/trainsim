package stage;

import rails.Rail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Alagút osztály. Csak NormalRail fölé helyezhető el. Egyszerre csak 1 példány lehet a pályára lehelyezve.
 */
public class Tunnel implements Serializable {

    /** Az alagút bejárata. */
    private Rail entrance;
    /** Az alagút kijárata. */
    private Rail exit;
    /** VÁLTOZTATÁS
     *  Tároljuk a síneket, melyek fölött alagút van.*/
    private List<Rail> railList = new ArrayList<Rail>();

    /**
     * Ha a paraméterben kapott sínre már van építve bejárat, akkor azt törli.
     * Ha még egy bejárat sincs lerakva, akkor lerakja a sínre.
     * Ha már van egy bejárat lerakva, megvizsgálja, hogy a lerakott bejárat
     * és a paraméterben kapott sín között lehet-e létrehozni alagutat, ha lehet, létrehozza.
     * @param rail a kattintott sín.
     * @return a sínek listája, amik fölött az alagút elhelyezkedik
     */
    public List<Rail> modifyTunnel(Rail rail) {
        if (rail.equals(entrance) || rail.equals(exit))
            return deleteTunnel(rail);
        else if (entrance == null)
            return createEntrance(rail);
        else if (exit == null)
            return createExit(rail);
        else
            return null;
    }

    /**
     * Kitörli a paraméterül kapott ki/bejáratot, visszaadja az alagútban lévő maradék síneket.
     * @param rail
     * @return az alagaút maradék része
     */
    private List<Rail> deleteTunnel (Rail rail) {
        //Set tunnel false on all rail
        for (Rail toBeDeleted : railList)
            toBeDeleted.setTunnel(false);

        if (rail.equals(entrance)) {
            if (exit == null) {
                railList.clear();
                entrance = null;
            }
            else {
                railList.clear();
                entrance = null;
                railList.add(exit);
            }
        }
        else if (rail.equals(exit)) {
            if (entrance == null) {
                railList.clear();
                exit = null;
            }
            else {
                railList.clear();
                exit = null;
                railList.add(entrance);
            }
        }
        // Set tunnel on all rail
        for (Rail toBeDeleted : railList)
            toBeDeleted.setTunnel(true);
        return railList;
    }

    /**
     * Megvizsgálja, hogy tehet-e le bejáratot az adott helyre.
     * Ha tehet, lerakja a bejáratot és visszaadja a sínek listáját, melyen alagút van.
     * @param entRail - az alagút bejárata
     * @return a sínek listája, amik fölött az alagút elhelyezkedik
     */
    private List<Rail> createEntrance(Rail entRail) {
        if (exit == null) {
            entrance = entRail;
            railList.clear();
            railList.add(entRail);
        }
        else {
            Rail temp = entRail;
            //Megnézzük, hogy a next irányban van-e, ha igen, feltöltjük a listát
            while (temp.canPlaceTunnel()) {
                if (temp.equals(exit)) {
                    entrance = entRail;
                    break;
                }
                if (temp.getNext() != null)
                    temp = temp.getNext();
                else
                    break;
            }
            if (temp.equals(exit)) {
                temp = entRail;
                railList.clear();
                while (!temp.equals(exit)) {
                    railList.add(temp);
                    temp = temp.getNext();
                }
                railList.add(exit);
            //Ha a next irányban nem volt, megnézzük a prev irányban is, és ha ott van, feltötljük a listát
            }
            else {
                temp = entRail;
                while (temp.canPlaceTunnel()) {
                    if (temp.equals(exit)) {
                        entrance = entRail;;
                        break;
                    }
                    if(temp.getPrevious() != null)
                        temp = temp.getPrevious();
                    else
                        break;
                }
                if (temp.equals(exit)) {
                    temp = entRail;
                    railList.clear();
                    while (!temp.equals(exit)) {
                        railList.add(temp);
                        temp = temp.getPrevious();
                    }
                    railList.add(exit);
                }
            }
        }
        for (Rail rail : railList)
            rail.setTunnel(true);
        return railList;
    }

    /**
     * Megvizsgálja, hogy tehet-e le kijáratot az adott helyre.
     * Ha tehet, lerakja a kijáratot és visszaadja a sínek listáját, melyen alagút van.
     * @param exRail - az alagút kijárata
     * @return a sínek listája, amik fölött az alagút elhelyezkedik
     */
    private List<Rail> createExit (Rail exRail) {
        Rail temp =  exRail;
        //Megnézzük, hogy a next irányban van-e, ha igen, feltöltjük a listát
        while (temp.canPlaceTunnel()) {
            if (temp.equals(entrance)) {
                exit = exRail;
                break;
            }
            if(temp.getNext() != null)
                temp = temp.getNext();
            else
                break;
        }
        if (temp.equals(entrance)) {
            temp =  exRail;
            railList.clear();
            while (!temp.equals(entrance)) {
                railList.add(temp);
                temp = temp.getNext();
            }
            railList.add(entrance);
            //Ha a next irányban nem volt, megnézzük a prev irányban is, és ha ott van, feltötljük a listát
        }
        else {
            temp = exRail;
            while (temp.canPlaceTunnel()) {
                if (temp.equals(entrance)) {
                    exit = exRail;
                    break;
                }
                if(temp.getPrevious() != null)
                    temp = temp.getPrevious();
                else
                    break;
            }
            if (temp.equals(entrance)) {
                temp =  exRail;
                railList.clear();
                while (!temp.equals(entrance)) {
                    railList.add(temp);
                    temp = temp.getPrevious();
                }
                railList.add(entrance);
            }
        }
        for (Rail rail : railList)
            rail.setTunnel(true);
        return railList;
    }
}
