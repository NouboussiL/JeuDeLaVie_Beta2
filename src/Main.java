import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import java.util.regex.Pattern;
import javax.swing.*;

public class Main {
    private static int ll = 0;
    private static int lc = 0;
    static int lmin = Integer.MAX_VALUE;
    static int lmax = Integer.MIN_VALUE;
    static int cmin = Integer.MAX_VALUE;
    static int cmax = Integer.MIN_VALUE;


    public static void main(String[] args) {


        //            vv
        //>>HAHA j'AI MODIFIé LE FICHIER<<
        //            ^^

        /*
        for(int i = 0; i< 5; i++){
            for(int j =5; j>-1;j--){
                grille.addMaillon(new Maillon(new Couple(j,i)));
            }
        }
        */
        /*Frame frame = new Frame(ll,lc);

        dessinerMatrice(frame,grille);*/

        List grille = new List();
        lireFichier(grille);
        System.out.println(grille.toString());
        List ng = genSuivante(grille);
        System.out.println(ng.toString());


    }


    public static void resetMatrice(Frame frame) {
        frame.remove(frame.pannel);
        frame.revalidate();
        frame.repaint();

    }


    public static void dessinerMatrice(Frame frame, List grille) {
        frame.pannel.removeAll();
        frame.dessinerMatrice(grille);
/*
        Maillon a = grille.tete;

        for(int i = 0; i< 2*ll;i++){
            for(int j = 0; j <2*lc;j++){
                JButton jb = new JButton();
                if(a!=null)
                if((ll+a.getLigne())==i&&(lc+a.getColonne())==j){
                    System.out.println(i+" "+j+" "+a.toString());
                    jb.setBackground(Color.BLACK);
                    a = a.getSuiv();
                }else{
                    jb.setVisible(false);
                }
                else{
                    jb.setVisible(false);
                }
                frame.pannel.add(jb);
            }
        }
        */
        frame.add(frame.pannel);
        frame.setVisible(true);
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.revalidate();
        frame.repaint();
    }


    public static List genSuivante(List grille) {
        List ngen = new List();
        List declaresMortes =new List();
        Maillon a = grille.tete;

        while (a != null) {
            int ligne = a.getLigne();
            int colonne = a.getColonne();

            verifierLesMortes(ligne, colonne, grille, ngen,declaresMortes);
            verifierSurvie(ligne, colonne, grille, ngen);
            a = a.getSuiv();
        }
        return ngen;
    }

    private static void verifierLesMortes(int ligne, int colonne, List grille, List ng, List declaresMortes) {
        //couples qui designent les vecteur pour les 8 directions
        Couple[] tc = {
                new Couple(-1, -1),
                new Couple(-1, 1),
                new Couple(1, -1),
                new Couple(1, 1),
                new Couple(0, 1),
                new Couple(0, -1),
                new Couple(1, 0),
                new Couple(-1, 0)};

        boolean dansGrille, dansNG, dansMortes;
        //rechercher les cases mortes dans chaque direction
        for (Couple vect : tc) {
            //ligne et colonne du vecteur
            int ligneVect = vect.getLigne();
            int colonneVect = vect.getColonne();

            //
            dansGrille = grille.estDans(ligne + ligneVect, colonne + colonneVect);
            dansNG = ng.estDans(ligne + ligneVect, colonne + colonneVect);
            dansMortes = declaresMortes.estDans(ligne + ligneVect, colonne + colonneVect);


            if (!dansGrille && !dansNG && !dansMortes) {
                //alors la case n'est pas dans la grille donc morte,n'as pas encore été
                // declaré definitivement morte et n'est pas encore été déclaré comme naissante
                int nbVoisines = calculerVoisines(ligne + ligneVect, colonne + colonneVect, grille);
                if (nbVoisines== 3) {
                    if (!dansNG)
                        //"its ALIVE!"(nb voisines est 3 et elle est pas encore presente
                        ng.addMaillon(new Maillon(ligne + ligneVect, colonne + colonneVect));
                }else
                {
                    //elle est morte
                    declaresMortes.addMaillon(new Maillon(ligne + ligneVect, colonne + colonneVect));
                }
            }
        }
    }

    private static void verifierSurvie(int ligne, int colonne, List grille, List ng) {
        int nbVoisines=calculerVoisines(ligne, colonne, grille);
        if ( nbVoisines == 2 || nbVoisines == 3)
            ng.addMaillon(new Maillon(ligne, colonne));
    }

    public static void lireFichier(List grille) {

        try {

            int ligne = 0;
            int colonne = 0;
            Scanner fs = new Scanner(new File("lifep/ACORN.LIF"));
            while (fs.hasNextLine()) {
                String s = fs.nextLine();
                if (s.matches("^#P.*")) {
                    String[] s2 = s.split(" ");
                    colonne = Integer.parseInt(s2[1]);
                    ligne = Integer.parseInt(s2[2]);
                    if (ligne < lmin)
                        lmin = ligne;
                    if (colonne < cmin)
                        cmin = colonne;
                } else {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (c == '*') {
                            Maillon maillon = new Maillon(ligne, i + colonne);
                            if ((i + colonne) > cmax)
                                cmax = i + colonne;
                            grille.addMaillon(maillon);
                        }
                    }
                    ligne--;
                    if (ligne > lmax)
                        lmax = ligne;
                }
            }
            ll = lmax - lmin;
            lc = cmax - cmin;
            System.out.println(ll + "  " + lc);

            fs.close();
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }

    private static int calculerVoisines(int x, int y, List grille) {
        return grille.getVoisines(x, y);
    }


}
