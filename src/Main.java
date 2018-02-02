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
        List ng = new List();
        Maillon a = grille.tete;
        while (a != null) {
            verifierLesMortes(a.getLigne(), a.getColonne(), grille, ng);
            verifierSurvie(a.getLigne(), a.getColonne(), grille, ng);
            a = a.getSuiv();
        }
        return ng;
    }

    private static void verifierLesMortes(int ligne, int colonne, List grille, List ng) {
        //couples qui designent les vecteur pour les 8 directions
        Couple[] tc = {

                new Couple(-1, -1),
                new Couple(-1, 1),
                new Couple(1, -1),
                new Couple(1, 1),
                new Couple(0, 1),
                new Couple(0, -1),
                new Couple(1, 0),
                new Couple(-1, 0),};
        //rechercher les cases mortes dans chaque direction
        for (Couple vect : tc) {
            if (!grille.estDans(ligne + vect.getLigne(), colonne + vect.getColonne())) {
                //alors la case est vide car n'est pas dans la liste
                if (calculerVoisines(ligne + vect.getLigne(), colonne + vect.getColonne(), grille) == 3) {
                    if (!ng.estDans(ligne + vect.getLigne(), colonne + vect.getColonne()))
                        //"its ALIVE!"(nb voisines est 3 et elle est pas encore presente
                        ng.addMaillon(new Maillon(ligne + vect.getLigne(), colonne + vect.getColonne()));

                }
            }
        }
    }

    private static void verifierSurvie(int ligne, int colonne, List grille, List ng) {
        if (calculerVoisines(ligne, colonne, grille) == 2 || calculerVoisines(ligne, colonne, grille) == 3)
            ng.addMaillon(new Maillon(ligne, colonne));
    }

    public static void lireFichier(List grille) {

        try {

            int ligne = 0;
            int colonne = 0;
            Scanner fs = new Scanner(new File("lifep/ACORN.LIF"));
            while (fs.hasNextLine()) {
                String s = fs.nextLine();
                String[] s2 = s.split(" ");
                if (s2[0].equals("#P")) {

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
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }

    private static int calculerVoisines(int x, int y, List grille) {
        return grille.getVoisines(x, y);
    }


}
