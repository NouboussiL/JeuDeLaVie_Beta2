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



        List grille = new List();
        lireFichier(grille, monde);
       /* Frame frame = new Frame(grille);
        dessinerMatrice(frame, grille);
        System.out.println(grille);
        try {
            Thread.sleep(6000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000; i++) {
            grille = genSuivante(grille);
            resetMatrice(frame);
            dessinerMatrice(frame, grille);
        }
*/
        System.out.println(grille+" init");
        List[] l = calculerProjections(grille);
        for (List x : l){
            System.out.println(x);
        }
        System.out.println(calculerSomme(grille,calculerProjections(grille)));


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
            Thread.sleep(100);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.revalidate();
        frame.repaint();
    }
    public static int min(Maillon[] listM){
        Maillon min = listM[0];
        int i=0;
        for (int j = 0; j < 8; j++) {
            if (listM[i].compareTo(min) < 0){
                min = listM[i];
            i = j;
            }
        }
        return i;


    }
    public static List calculerSomme(List grille , List[] projects){
        List somme = new List();
        Maillon a,b,c,d,e,f,g,h;
        a=projects[0].tete;
        b=projects[1].tete;
        c=projects[2].tete;
        d=projects[3].tete;
        e=projects[4].tete;
        f=projects[5].tete;
        g=projects[6].tete;
        h=projects[7].tete;
        Maillon[] listM= new Maillon[8];
        for (int i = 0; i < 8; i++) {
            listM[i]=projects[i].tete;
        }

        while (!estTraite(listM)){


            int min =min(listM);
            Maillon sm= sommeMaillon(min,listM);
            somme.addMaillon(sm);

        }


        return somme;

    }

    private static Maillon sommeMaillon(int min, Maillon[] listM) {

        Maillon m= new Maillon(listM[min].getLigne(),listM[min].getColonne(),0);
        for (Maillon x : listM){
            if (x.compareTo(m)==0){
                m.setNbvois(m.getNbvois()+1);
                x=x.getSuiv();
            }

        }


        return m;

    }

    private static boolean estTraite(Maillon[] listM) {
        for (Maillon x :listM){
            if (x!=null) return false;
        }
        return true;
    }


    public static List genSuivante(List grille) {
        return grille.eliminerNonConformes();
    }

    private static List[] calculerProjections( List grille) {

    List [] project = new List[8];
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
        for (int i = 0; i < 8; i++) {
            project[i] = List.projeter(grille,tc[i]);
        }
    return project;
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
            Scanner fs = new Scanner(new File("lifep/TEST.LIF"));
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
                            Maillon maillon = new Maillon(ligne, i + colonne,1);
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




}
