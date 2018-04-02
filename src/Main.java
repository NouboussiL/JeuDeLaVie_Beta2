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
        Jeu jeu = new Jeu();
        List l = jeu.getList();
        lireFichier(jeu.getList(), monde);
        //Frame frame = new Frame(l);
        //dessinerMatrice(frame, l);
        System.out.println(l);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 1000; i++) {
            jeu = jeu.nextGen();
            l=jeu.getList();
            System.out.println(jeu.toString());
            /*resetMatrice(frame);
            dessinerMatrice(frame, grille);*/
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
            Thread.sleep(100);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.revalidate();
        frame.repaint();
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
                            Cellule cellule = new Cellule(ligne, colonne);
                            Maillon maillon = new Maillon(cellule);
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
