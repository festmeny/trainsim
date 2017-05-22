package game;

import com.sun.org.apache.xpath.internal.operations.Bool;
import stage.Stage;

import javax.swing.*;
import java.io.*;
import java.util.Scanner;

/**
 * A szimuláció legmagasabb szintje. Itt tud beleavatkozni a játékos a folyamatokba
 * a váltó (Switch) állításával, alagút (Tunnel) építésével.
 */
public class Game {
    public static void main(String[] args) {
        StartMenu sm = new StartMenu();
    }
}
