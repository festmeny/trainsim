package stage;

import rails.*;
import trains.Wagon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Draw extends JFrame {
    public Rail map[][];

    public Draw(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(606, 629);
        this.setResizable(false);
        this.add(pane);
        this.setVisible(true);
    }

    public JPanel pane = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            BufferedImage image = null;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (map[i][j] == null) {
                        // Grass
                        try {
                            image = ImageIO.read(new File("graphics/grass.png"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (map[i][j].getTunnel()) {
                        // Tunnel
                        try {
                            image = ImageIO.read(new File("graphics/tunnel.png"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (map[i][j].getLocomotive()) {
                        // Loco
                        try {
                            image = ImageIO.read(new File("graphics/locomotive.png"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if (map[i][j].getWagon() != null) {
                        Wagon wagon = map[i][j].getWagon();
                        if (wagon.isEmpty()) {
                            try {
                                image = ImageIO.read(new File("graphics/wagonempty.png"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            switch (wagon.getColor()) {
                                case RED:
                                    try {
                                        image = ImageIO.read(new File("graphics/wagonred.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case GREEN:
                                    try {
                                        image = ImageIO.read(new File("graphics/wagongreen.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case BLUE:
                                    try {
                                        image = ImageIO.read(new File("graphics/wagonblue.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case YELLOW:
                                    try {
                                        image = ImageIO.read(new File("graphics/wagonyellow.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case ORANGE:
                                    try {
                                        image = ImageIO.read(new File("graphics/wagonorange.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case BLACK:
                                    try {
                                        image = ImageIO.read(new File("graphics/wagonblack.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }
                    }
                    else {
                        // NormalRail
                        if (map[i][j] instanceof NormalRail) {
                            try {
                                image = ImageIO.read(new File("graphics/normalrail.png"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        // CrossRail
                        else if (map[i][j] instanceof CrossRail) {
                            try {
                                image = ImageIO.read(new File("graphics/crossrail.png"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        // SwitchRail
                        else if (map[i][j] instanceof SwitchRail) {
                                SwitchRail switchRail = (SwitchRail) map[i][j];
                                if (i-1>=0  && switchRail.getPrevious() == map[i - 1][j]) {
                                    if (switchRail.getNext() == map[i][j + 1]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/leftbottomrail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (switchRail.getNext() == map[i + 1][j]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/verticalrail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (switchRail.getNext() == map[i][j - 1]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/rightbottomrail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if (j+1 < 6 && switchRail.getPrevious() == map[i][j + 1]) {
                                    if (switchRail.getNext() == map[i + 1][j]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/lefttoprail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (switchRail.getNext() == map[i][j - 1]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/horizontalrail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (switchRail.getNext() == map[i - 1][j]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/leftbottomrail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if (i+1 < 6 && switchRail.getPrevious() == map[i + 1][j]) {
                                    if (switchRail.getNext() == map[i][j - 1]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/righttoprail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (switchRail.getNext() == map[i - 1][j]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/verticalrail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (switchRail.getNext() == map[i][j + 1]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/lefttoprail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else if (j-1 >= 0 && switchRail.getPrevious() == map[i][j - 1]) {
                                    if (switchRail.getNext() == map[i - 1][j]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/rightbottomrail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (switchRail.getNext() == map[i][j + 1]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/horizontalrail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (switchRail.getNext() == map[i + 1][j]) {
                                        try {
                                            image = ImageIO.read(new File("graphics/righttoprail.png"));
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                        }
                        // StationRail
                        else if (map[i][j] instanceof StationRail) {
                            StationRail station = (StationRail) map[i][j];
                            switch (station.getColor()) {
                                case RED:
                                    try {
                                        image = ImageIO.read(new File("graphics/stationred.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case GREEN:
                                    try {
                                        image = ImageIO.read(new File("graphics/stationgreen.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case BLUE:
                                    try {
                                        image = ImageIO.read(new File("graphics/stationblue.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case YELLOW:
                                    try {
                                        image = ImageIO.read(new File("graphics/stationyellow.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case ORANGE:
                                    try {
                                        image = ImageIO.read(new File("graphics/stationorange.png"));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }
                    }
                    g.drawImage(image, j * 100, i * 100, null);
                }
            }
        }
    };

    public void drawToDisplay(){
        pane.repaint();
    }

}
