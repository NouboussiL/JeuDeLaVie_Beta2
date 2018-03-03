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
    static int a=0;
    public enum Mondes {
        CIRCULAIRE,
        FRONTIERE,
        INFINI;
    }

    public static void main(String[] args) {
        List grille = new List();
        lireFichier(grille, monde);
        Frame frame = new Frame(grille);
        dessinerMatrice(frame, grille);
        System.out.println(grille);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000; i++) {
            grille=nextGen(grille);

            /*resetMatrice(frame);
            dessinerMatrice(frame, grille);*/
        }
    }

    public static List nextGen(List grille){
        List ng =calculerSomme(calculerProjections(grille));
        System.out.println(ng);

        ng = sommeM0Nbvois(grille,ng);
        a++;
        ng=ng.eliminerNonConformes();
        return ng;

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
            if (listM[j]!=null && listM[j].compareTo(min) < 0){
                min = listM[j];
                i = j;
            }
        }
        return i;
    }

    public static List calculerSomme(List[] projects){
        List somme = new List();

        Maillon[] listM= new Maillon[8];
        for (int i = 0; i < 8; i++) {
            listM[i]=projects[i].tete;
        }
        while (!estTraite(listM)){
            int min =min(listM);
            Maillon sm= chercherVoisins(min,listM);
            somme.addMaillon(sm);
        }
        return somme;
    }

    private static Maillon chercherVoisins(int min, Maillon[] listM) {
        Maillon m= new Maillon(listM[min].getLigne(),listM[min].getColonne(),0);
        for (int i = 0; i < 8; i++) {
            if (listM[i]!=null&&listM[i].compareTo(m)==0) {
                m.setNbvois(m.getNbvois()+1);
                listM[i]=listM[i].getSuiv();
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

    private static List sommeM0Nbvois(List m0, List somme){
        List m0mille=m0.initM0();
       /* Maillon b = somme.tete;

        while (b!=null ){
            Maillon a= m0mille.tete;
            while (a!=null){
                if(a.compareTo(b)==0){
                    b.setNbvois(b.getNbvois()+1000);
                    break;
                }
                a=a.getSuiv();
            }
            b=b.getSuiv();
        }*/

        Maillon a= m0mille.tete;
        while (a!=null){
            Maillon b = somme.tete;
            while(b!=null){
                if(a.compareTo(b)==0){
                    b.setNbvois(b.getNbvois()+1000);
                    break;
                }
                b=b.getSuiv();
            }
                a=a.getSuiv();
        }


        return somme;
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
            Scanner fs = new Scanner(new File("lifep/ACORN.LIF"));
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
            fs.close();
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }




}
