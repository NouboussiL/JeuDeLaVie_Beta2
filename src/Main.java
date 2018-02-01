import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.*;
import java.util.regex.Pattern;
import javax.swing.*;

public class Main {
    private static int ll=0;
    private static int lc=0;
    static int lmin=Integer.MAX_VALUE;
    static int lmax=Integer.MIN_VALUE;
    static int cmin=Integer.MAX_VALUE;
    static int cmax=Integer.MIN_VALUE;


    public static void main(String[] args){
        List grille = new List();
        /*
        for(int i = 0; i< 5; i++){
            for(int j =5; j>-1;j--){
                grille.addMaillon(new Maillon(new Couple(j,i)));
            }
        }
        */
        lireFichier(grille);
        System.out.println(grille.toString());
        System.out.println(grille.getVoisines(-3,-16));
        Frame frame = new Frame(ll,lc);

        dessinerMatrice(frame,grille);

        for(int i = 0; i<1000;i++){
            grille = calculerNextGen(grille);
            resetMatrice(frame);
            dessinerMatrice(frame,grille);
        }
        System.out.println(grille.toString());
    }


    public static void resetMatrice(Frame frame){
        frame.remove(frame.pannel);
        frame.revalidate();
        frame.repaint();

    }


    public static void dessinerMatrice(Frame frame,List grille){
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
        frame.add( frame.pannel);
        frame.setVisible(true);
        try {
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        frame.revalidate();
        frame.repaint();
    }

    public static void lireFichier(List grille){

        try{

            int ligne=0;
            int colonne=0;
            Scanner fs = new Scanner(new File("lifep/EDEN.LIF"));
            while(fs.hasNextLine()){
                String s = fs.nextLine();
                String[] s2 = s.split(" ");
                if (s2[0].equals("#P")) {

                    colonne = Integer.parseInt(s2[1]);
                    ligne = Integer.parseInt(s2[2]);

                    if(ligne<lmin)lmin=ligne;
                    if(colonne<cmin)cmin=colonne;

                }else {
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (c == '*') {
                            Maillon maillon = new Maillon(ligne, i+colonne);
                            if((i+colonne)>cmax)cmax=i+colonne;
                            grille.addMaillon(maillon);
                        }
                    }
                    ligne++;
                    if(ligne>lmax)lmax=ligne;
                }


            }
            ll = lmax-lmin;
            lc=cmax-cmin;
            System.out.println(ll+"  "+lc);
        }catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }

    private static int calculerVoisines(int x,int y,List grille){
        return grille.getVoisines(x,y);
    }

    private static List calculerNextGen(List grille){
        List nextGen = new List();
        Maillon a = grille.tete;
        int sum =0;
        for(int i = lmin; i< lmax;i++) {
            System.out.println("ligne "+i+"\n_________");
            for (int j = cmin; j < cmax; j++) {
                sum = calculerVoisines(i,j,grille);
                if (a != null){
                    System.out.println(a.toString());
                    if ((a.getLigne()) == j && (a.getColonne()) == i) {
                        if(sum == 2 || sum == 3) nextGen.addMaillon(new Maillon(i,j));
                        a = a.getSuiv();
                    }
                }
                if(sum==3)nextGen.addMaillon(new Maillon(i,j));
            }
        }
        return nextGen;
    }
}
