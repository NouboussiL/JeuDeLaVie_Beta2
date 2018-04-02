public class Jeu {

    private List grille;


    public Jeu(){
        grille = null;
    }


    private void setGrille(List list){
        this.grille = list;
    }

    public List getList(){
        return grille;
    }




    public Jeu nextGen(){
        Jeu nouveau = new Jeu();
        List ng =calculerSomme(calculerProjections());
        System.out.println(ng);

        ng = sommeM0Nbvois(grille,ng);
        nouveau.setGrille(eliminerNonConformes());

        return nouveau;
    }

    private List eliminerNonConformes() {
        Maillon a = grille.getTete();
        List ng = new List();
        while(a!=null){
            Cellule c = (Cellule)a.getInfo();
            int nbVois = c.getNbvois();
            if(nbVois==3 || nbVois==1002 || nbVois==1003){
                ng.addMaillon(new Maillon(new Cellule(c.getLigne(),c.getColonne())));


            }
            a=a.getSuiv();
        }
        return ng;
    }


    private int min(Maillon[] listM){
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

    private List calculerSomme(List[] projects){
        List somme = new List();

        Maillon[] listM= new Maillon[8];
        for (int i = 0; i < 8; i++) {
            listM[i]=projects[i].getTete();
        }
        while (!estTraite(listM)){
            int min =min(listM);
            Maillon sm= chercherVoisins(min,listM);
            somme.addMaillon(sm);
        }
        return somme;
    }

    private Maillon chercherVoisins(int min, Maillon[] listM) {
        Cellule c = (Cellule)listM[min].getInfo();
        Cellule d  = new Cellule(c.getLigne(),c.getColonne(),0);
        Maillon m= new Maillon(d);
        for (int i = 0; i < 8; i++) {
            if (listM[i]!=null&&listM[i].compareTo(m)==0) {
                Cellule courante = (Cellule)m.getInfo();
                courante.setNbvois(courante.getNbvois()+1);
                listM[i]=listM[i].getSuiv();
            }
        }

        return m;
    }

    private boolean estTraite(Maillon[] listM) {
        for (Maillon x :listM){
            if (x!=null) return false;
        }
        return true;
    }


    private List projeter(Couple couple) {
        List l = new List();
        Maillon a = grille.getTete();

        while(a!=null){
            Cellule c= (Cellule)a.getInfo();
            Cellule nouvelle = new Cellule(c.getLigne()+couple.getLigne(),c.getColonne()+couple.getColonne());
            l.addMaillon(new Maillon(nouvelle));
            a=a.getSuiv();
        }
        return l;
    }


    private List initM0(){
        List nl= new List();
        Maillon a = grille.getTete();

        while(a!=null){
            Cellule c = (Cellule)a.getInfo();
            nl.addMaillon(new Maillon(new Cellule(c.getLigne(),c.getColonne(),1000)));
            a=a.getSuiv();
        }
        return nl;
    }


    private List sommeM0Nbvois(List m0, List somme){
        List m0mille=initM0();
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

        Maillon a= m0mille.getTete();
        while (a!=null){
            Maillon b = somme.getTete();
            while(b!=null){
                if(a.compareTo(b)==0){
                    Cellule fromB = (Cellule)b.getInfo();
                    Cellule fromA = (Cellule)a.getInfo();
                    fromB.setNbvois(fromB.getNbvois()+fromA.getNbvois());
                    break;
                }
                b=b.getSuiv();
            }
            a=a.getSuiv();
        }


        return somme;
    }

    private List[] calculerProjections() {
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
            project[i] = projeter(tc[i]);
        }
        return project;
    }


    @Override
    public String toString(){
        return grille.toString();
    }
}
