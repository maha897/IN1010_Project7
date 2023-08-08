public class Kontroll {
    private GUI gui;
    private Modell modell;
    private Retning retning;
    private Thread trad;

    Kontroll(){
        retning = Retning.HOYRE;
        gui = new GUI(this);
        modell = new Modell(this, gui);
        trad = new Thread(new Teller());
    }

    void startSpill(){
        for (int i = 0; i < 10; i++){
            modell.genererSnack();
        }
        trad.start();
        gui.tegn(5, 5);
    }

    void flytt(){
        modell.flytt(retning);
    }

    void byttRetning(Retning retning){
        this.retning = retning;
    }

    void vunnet(){
        trad.interrupt();
        gui.visVunnet();
        avslutt();
    }

    void tapt(){
        trad.interrupt();
        gui.visTapt();
        avslutt();
    }

    void avslutt(){
        System.exit(0);
    }

    public int hentLengde(){
        return modell.hentLengde();
    }

    class Teller implements Runnable{
        @Override 
        public void run(){
            while(true){
                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e){}
                flytt();
            }
        }
    }
}
