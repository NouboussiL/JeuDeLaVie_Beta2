import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    JPanel2 pannel = new JPanel2();
    int lligne, lcolonne;


    public Frame(List grille) {
        super("Cyka nuggets");
        //getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        //this.taille = 50;
        setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height - 100));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        System.out.println(getContentPane().getPreferredSize());

        getContentPane().add(pannel);
        pannel.setSize(getContentPane().getPreferredSize().width, getContentPane().getPreferredSize().height);

        //pannel.setLayout(new GridLayout(2*this.ll, 2*lc));
        lligne = grille.getLongueurLigne();
        lcolonne = grille.getLongueurColonne();
        System.out.println(lcolonne + " " + lligne);

    }

    public void dessinerMatrice(List grille) {
        getContentPane().remove(pannel);
        pannel = new JPanel2(grille, lligne, lcolonne);
        //pannel.setSize(0,0);
        getContentPane().add(pannel);
    }


    class JPanel2 extends JPanel {

        List grille;
        int longLigne, longColonne;

        public JPanel2() {
            super();
            grille = null;
            longLigne = longColonne = 0;
        }

        public JPanel2(List grille, int longLigne, int longColonne) {
            this.grille = grille;
            this.longLigne = longLigne;
            this.longColonne = longColonne;
        }

        @Override
        public void paintComponent(Graphics g) {
            Maillon a = grille.tete;
            System.out.println((getWidth() - grille.getLongueurLigne()) / 2 + "  " + (getHeight() - grille.getLongueurColonne()) / 2);

            while (a != null) {
                //ne pas chercher à comprendre comment ça marche(!)
                g.fillOval((getWidth() - grille.getLongueurLigne()) / 2 + a.getColonne() * 7,
                        (getHeight() - grille.getLongueurColonne()) / 2 - a.getLigne() * 7, 7, 7);
                a = a.getSuiv();
            }
        }

    }


}