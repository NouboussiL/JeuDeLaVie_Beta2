import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import java.util.regex.Pattern;
import javax.swing.*;

public class Main {
    static int taille;
    static Mondes monde;

    public enum Mondes {
        CIRCULAIRE,
        FRONTIERE,
        INFINI;
    }

    public static void main(String[] args) {



        /*
        for(int i = 0; i< 5; i++){
            for(int j =5; j>-1;j--){
                grille.addMaillon(new Maillon(new Couple(j,i)));
            }
        }
        */

        /*
        //Choix du type de monde à lancer
        System.out.println("Choisissez le type de monde");
        int i =1;
        for(Mondes monde : Mondes.values()){
            System.out.println("    "+i+" : "+monde);
            i++;
        }

        String s = new Scanner(System.in).next();
        int menu = Integer.parseInt(s);
        switch(menu){
            case 1 : monde = Mondes.CIRCULAIRE;
                break;
            case 2 : monde = Mondes.FRONTIERE;
                break;
            case 3 : monde = Mondes.INFINI;
        }


        if(monde != Mondes.INFINI) {
            System.out.print("Taille de la grille : ");
            taille = Integer.parseInt(new Scanner(System.in).next());
        }
        */

        List grille = new List();
        lireFichier(grille, monde);
        Frame frame = new Frame(grille);
        dessinerMatrice(frame, grille);
        System.out.println(grille);
        for (int i = 0; i < 1000; i++) {
            grille = genSuivante(grille);
            resetMatrice(frame);
            dessinerMatrice(frame, grille);
        }



    }


    public static void resetMatrice(Frame frame) {
        frame.remove(frame.pannel);
        frame.revalidate();
        frame.repaint();

    }


    public static void dessinerMatrice(Frame frame, List grille) {
        frame.pannel.removeAll();
        frame.dessinerMatrice(grille);

        frame.add(frame.pannel);
        frame.setVisible(true);
        try {
            Thread.sleep(300);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.revalidate();
        frame.repaint();
    }


    public static List genSuivante(List grille) {
        List ngen = new List();
        List declaresMortes = new List();
        Maillon a = grille.tete;

        while (a != null) {
            int ligne = a.getLigne();
            int colonne = a.getColonne();

            verifierLesMortes(ligne, colonne, grille, ngen, declaresMortes);
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
            //ligne et colonne de la case morte voisine à la case vivante
            int ligneVoisine = ligne + vect.getLigne();
            int colonneVoisine = colonne + vect.getColonne();

            //regarder si la voisine est dans l'une des grilles
            dansGrille = grille.estDans(ligneVoisine, colonneVoisine);
            dansNG = ng.estDans(ligneVoisine, colonneVoisine);
            dansMortes = declaresMortes.estDans(ligneVoisine, colonneVoisine);


            if (!dansGrille && !dansNG && !dansMortes) {
                //alors la case n'est pas dans la grille donc morte,n'as pas encore été
                // declaré definitivement morte et n'est pas encore été déclaré comme naissante
                int nbVoisines = calculerVoisines(ligneVoisine, colonneVoisine, grille);
                if (nbVoisines == 3) {
                    if (!dansNG)
                        //"its ALIVE!"(nb voisines est 3 et elle est pas encore presente
                        ng.addMaillon(new Maillon(ligneVoisine, colonneVoisine));
                } else {
                    //elle est morte
                    declaresMortes.addMaillon(new Maillon(ligneVoisine, colonneVoisine));
                }
            }
        }
    }

    private static void verifierSurvie(int ligne, int colonne, List grille, List ng) {
        int nbVoisines = calculerVoisines(ligne, colonne, grille);
        if (nbVoisines == 2 || nbVoisines == 3)
            ng.addMaillon(new Maillon(ligne, colonne));
    }

    public static void lireFichier(List grille, Mondes monde) {
        int ligneMax, colonneMax, ligneMin, colonneMin;
        try {
            if (monde != Mondes.INFINI) {
                ligneMax = colonneMax = taille / 2;
                ligneMin = colonneMin = taille / 2 + 1;

            } else {
                ligneMax = colonneMax = Integer.MAX_VALUE;
                ligneMin = colonneMin = Integer.MIN_VALUE;
            }
            int ligne = 0;
            int colonne = 0;

            Scanner fs = new Scanner(new File("lifep/BARGE.LIF"));
            while (fs.hasNextLine()) {
                String s = fs.nextLine();
                if (s.matches("^#P.*")) {
                    String[] s2 = s.split(" ");
                    colonne = Integer.parseInt(s2[1]);
                    ligne = Integer.parseInt(s2[2]);
                } else {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (c == '*') {
                            Maillon maillon = new Maillon(ligne, i + colonne);
                            grille.addMaillon(maillon);
                        }
                    }
                    ligne--;
                }
            }
            System.out.println(grille.getLongueurLigne() + "  " + grille.getLongueurColonne());

            fs.close();
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }

    private static int calculerVoisines(int x, int y, List grille) {
        return grille.getVoisines(x, y);
    }


}
