import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GUI {
    private final int DIMENSJON;
    private Kontroll kontroll;
    private JFrame vindu;
    private JPanel panel, trykkPanel, rutenett;
    private JLabel[][] ruter;
    private JLabel lengdeLabel;
    private JButton opp, ned, venstre, hoyre;

    GUI(Kontroll kontroll){
        DIMENSJON = 12;
        ruter = new JLabel[DIMENSJON][DIMENSJON];
        this.kontroll = kontroll;
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e){
            System.exit(0);
        }

        vindu = new JFrame("Snake");
        vindu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(lagRutenett(), BorderLayout.NORTH);
        panel.add(lagTrykkPanel(), BorderLayout.SOUTH);

        vindu.add(panel);
        vindu.pack();
        vindu.setVisible(true);
    }

    private JPanel lagRutenett(){
        rutenett = new JPanel();
        rutenett.setLayout(new GridLayout(DIMENSJON, DIMENSJON));
        for (int rad = 0; rad < DIMENSJON; rad++){
            for (int kolonne = 0; kolonne < DIMENSJON; kolonne++){
                JLabel rute = new JLabel(" ", SwingConstants.CENTER);
                rute.setPreferredSize(new Dimension(20, 20));
                rute.setBorder(BorderFactory.createLineBorder(new Color(245, 242, 242)));
                rute.setBackground(Color.LIGHT_GRAY);
                rute.setOpaque(true);
                ruter[rad][kolonne] = rute;
                rutenett.add(rute);
            }
        }
        return rutenett;
    }

    private JPanel lagTrykkPanel(){
        trykkPanel = new JPanel();
        trykkPanel.setLayout(new BorderLayout());

        lengdeLabel = new JLabel("Lengde: 1");
        trykkPanel.add(lengdeLabel, BorderLayout.WEST);   
        
        JPanel taster = new JPanel();
        taster.setLayout(new BorderLayout());
        trykkPanel.add(taster, BorderLayout.CENTER);

        opp = new JButton("^");
        ned = new JButton("v");
        venstre = new JButton("<");
        hoyre = new JButton(">");

        class RetningBytter implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e){
                JButton source = (JButton)e.getSource();
                if (source == opp){
                    kontroll.byttRetning(Retning.OPP);
                } if (source == ned){
                    kontroll.byttRetning(Retning.NED);
                } if (source == venstre){
                    kontroll.byttRetning(Retning.VENSTRE);
                } if (source == hoyre){
                    kontroll.byttRetning(Retning.HOYRE);
                }
            }
        }
        opp.addActionListener(new RetningBytter());
        ned.addActionListener(new RetningBytter());
        venstre.addActionListener(new RetningBytter());
        hoyre.addActionListener(new RetningBytter());

        taster.add(opp, BorderLayout.NORTH);
        taster.add(ned, BorderLayout.SOUTH);
        taster.add(venstre, BorderLayout.WEST);
        taster.add(hoyre, BorderLayout.EAST);
        
        JButton slutt = new JButton("Avslutt");
        class Avslutt implements ActionListener{
            @Override 
            public void actionPerformed(ActionEvent e){
                kontroll.avslutt();
            }
        }
        slutt.addActionListener(new Avslutt());
        trykkPanel.add(slutt, BorderLayout.EAST);

        return trykkPanel;
    }

    void tegnSlange(ArrayList<Posisjon> slange){
        int teller = 0;
        for (Posisjon posisjon: slange){
            JLabel slangeLedd = ruter[posisjon.hentY()][posisjon.hentX()];
            if (teller == 0){
                harBesokt(posisjon.hentX(), posisjon.hentY());
            } else if (teller == slange.size()-1){
                slangeLedd.setText("o");
                slangeLedd.setBackground(Color.GRAY);
                slangeLedd.setForeground(Color.DARK_GRAY);
            } else {
                slangeLedd.setText("+");
                slangeLedd.setBackground(Color.GRAY);
                slangeLedd.setForeground(Color.DARK_GRAY);
            }
            teller++;
        }
    }

    void tegn(int posisjonX, int posisjonY){
        JLabel hodeStart = new JLabel("o");
        ruter[posisjonY][posisjonX] = hodeStart;
        hodeStart.setText("o");
        hodeStart.setBackground(Color.GRAY);
        hodeStart.setForeground(Color.DARK_GRAY);
    }


    public void harBesokt(int posisjonX, int posisjonY){
        JLabel etterHale = ruter[posisjonY][posisjonX];
        etterHale.setText(" ");
        etterHale.setBackground(Color.LIGHT_GRAY);
        etterHale.setForeground(Color.BLACK);
    }

    void tegnSnacks(int posX, int posY){
        ruter[posY][posX].setText("$");
        ruter[posY][posX].setForeground(Color.PINK);
        ruter[posY][posX].setBackground(Color.WHITE);
    }

    void oppdaterLengde(){
        lengdeLabel.setText("Lengde:"+kontroll.hentLengde());
    }

    public void visVunnet(){
        JOptionPane.showMessageDialog(vindu, "Du vant!", "Spillet er over!", JOptionPane.CLOSED_OPTION);
    }

    public void visTapt(){
        JOptionPane.showMessageDialog(vindu, "Du tapte", "Spiller er over!", JOptionPane.CLOSED_OPTION);
    }
}
