import javax.swing.*;
import java.awt.*;
public class Frame extends JFrame {
    JPanel2 pannel=new JPanel2();
    int lligne,lcolonne;



    public Frame(List grille) {
        super("Cyka nuggets");
        //getContentPane().setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        //this.taille = 50;
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pannel.setSize(getContentPane().getPreferredSize().width,getContentPane().getPreferredSize().height);
        getContentPane().add(pannel);
        //pannel.setLayout(new GridLayout(2*this.ll, 2*lc));
        lligne = grille.getLongueurLigne();
        lcolonne = grille.getLongueurColonne();

    }

    public void dessinerMatrice(List grille){
        getContentPane().remove(pannel);
        pannel = new JPanel2(grille,lligne,lcolonne);
        pannel.setSize(0,0);
        getContentPane().add(pannel);
    }



    class JPanel2 extends JPanel{

        List grille;
        int longLigne,longColonne;

        public JPanel2(){
            super();
            grille = null;
            longLigne = longColonne = 0;

        }
        public JPanel2(List grille,int  longLigne, int longColonne){
            this.grille = grille;
            this.longLigne = longLigne;
            this.longColonne = longColonne;
        }

        @Override
        public void paintComponent(Graphics g){
            Maillon a = grille.tete;
            while(a!=null){
                g.fillOval((getContentPane().getPreferredSize().width+10*longColonne)/2+10*a.getColonne(),(getContentPane().getPreferredSize().height+10*longLigne)/2-10*a.getLigne(),10,10);
                a=a.getSuiv();
            }


        }

    }




}