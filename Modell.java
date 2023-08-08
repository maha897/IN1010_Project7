import java.util.ArrayList;

public class Modell{
    private final int DIMENSJON = 12;
    private Kontroll kontroll;
    private GUI gui;
    private Slange slange;
    private char[][] brett;

    Modell(Kontroll kontroll, GUI gui){
        brett = new char[DIMENSJON][DIMENSJON];
        this.kontroll = kontroll;
        this.gui = gui;
        
        for (int rad = 0; rad < DIMENSJON; rad++){
            for (int kolonne = 0; kolonne < DIMENSJON; kolonne++){
                brett[rad][kolonne] = ' ';
            }
        }

        int xstart = 5; int ystart = 5;
        slange = new Slange(ystart, xstart);
        gui.tegnSlange(slange.hentSlange());
    }

    void flytt(Retning retning){
        slange.beveg(retning); 
        if (sjekkVunnet()) gui.visVunnet();
    }

    void genererSnack(){
        int xPos = tilfeldig();
        int yPos = tilfeldig();
        brett[yPos][xPos] = '$';
        gui.tegnSnacks(xPos, yPos);
    }

    private int tilfeldig(){
        return (int)(Math.random()*DIMENSJON);
    }

    int hentLengde(){
        return slange.lengde;
    }

    private boolean sjekkVunnet(){
        if (slange.hentSlange().size() == DIMENSJON*DIMENSJON) return true;
        else return false;
    }

    class Slange{
        ArrayList <Posisjon> slange = new ArrayList<>();
        int posisjonX, posisjonY;
        private int lengde;

        Slange(int posisjonX, int posisjonY){
            this.posisjonX = posisjonX;
            this.posisjonY = posisjonY;
            slange.add(new Posisjon(posisjonX, posisjonY));
            lengde = 1;
        }

        void beveg(Retning retning){
            int midX = posisjonX;
            int midY = posisjonY;

            switch (retning){
                case OPP:
                    midY--;
                    break;
                case NED:
                    midY++;
                    break;
                case VENSTRE:
                    midX--;
                    break;
                case HOYRE:
                    midX++;
                    break;
            }
            if (gyldigPosisjon(midX, midY)){
                if (brett[midY][midX] == '$'){
                    spisSnack(midX, midY);
                    genererSnack();
                    gui.oppdaterLengde();
                } else {
                    gui.harBesokt(posisjonX, posisjonY);
                }
                posisjonX = midX;
                posisjonY = midY;

                slange.add(new Posisjon(posisjonX, posisjonY));
                gui.tegnSlange(hentSlange());
                slange.remove(0);
            } else kontroll.tapt();
        }

        void spisSnack(int x, int y){
            slange.add(0, slange.get(0));
            lengde++;
        }

        public ArrayList <Posisjon> hentSlange(){
            return slange;
        }

        private boolean gyldigPosisjon(int x, int y){
            if (x < 0 || x >= DIMENSJON) return false;
            if (y < 0 || y >= DIMENSJON) return false;
            for (Posisjon posisjon: slange){
                if (posisjon.hentX() == x && posisjon.hentY() == y){
                    return false;
                }
            }
            return true;
        }
    }
}