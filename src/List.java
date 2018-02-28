public class List {

    Maillon tete;
    int lmin = Integer.MAX_VALUE;
    int lmax = Integer.MIN_VALUE;
    int cmin = Integer.MAX_VALUE;
    int cmax = Integer.MIN_VALUE;
    int longueurColonne;
    int longueurLigne;

    public List(){
        tete = null;
    }

    public void addMaillon(Maillon maillon){
        if(tete == null){
            tete = maillon;
            insere(maillon.getLigne(),maillon.getColonne());
        }
        else{
            int ligne = maillon.getLigne();
            int colonne = maillon.getColonne();
            if(maillon.compareTo(tete)==-1){
                maillon.setSuivant(tete);
                tete = maillon;
                insere(ligne,colonne);
            }else{
                int comp=-1;
                Maillon a =tete.getSuiv();
                Maillon b = tete;
                while(a!=null&&((comp = maillon.compareTo(a))>0)){
                    a =a.getSuiv();
                    b = b.getSuiv();

                }
                if(comp != 0) {
                    maillon.setSuivant(a);
                    b.setSuivant(maillon);
                    insere(ligne,colonne);
                }
            }

        }
    }

    private void insere(int ligne, int colonne){
        cmin = cmin > colonne ? colonne : cmin;
        cmax = cmax>colonne ? cmax : colonne;
        lmin = lmin > ligne ? ligne : lmin;
        lmax = lmax >ligne ? lmax : ligne;
        longueurColonne = cmax-cmin+1;
        longueurLigne = lmax-lmin+1;
    }

    @Override
    public String toString(){
        String s = "[";
        Maillon a = tete;
        while(a!=null){
            if(a.getSuiv()==null) {
                s += a.toString();
            }else{
                s+=a.toString()+",";
            }
            a=a.getSuiv();
        }
        s+="]";
        return s;
    }


    public boolean estDans(int ligne,int colonne){
        Maillon a = tete;

        while(a!=null){
            if(a.getLigne()==ligne && a.getColonne()==colonne)
                return true;
            a=a.getSuiv();
        }
        return false;

    }


    public int getVoisines(int ligne, int colonne){
        Maillon a = tete;
        int sum=0;
        while(a!=null){
            if(a.getLigne()==(ligne-1) && a.getColonne()==(colonne+1))sum++;
            if(a.getLigne()==(ligne-1) && a.getColonne()==(colonne-1))sum++;
            if(a.getLigne()==(ligne-1)&& a.getColonne()==(colonne))sum++;
            if(a.getLigne()==(ligne+1) && a.getColonne()==(colonne-1))sum++;
            if(a.getLigne()==(ligne+1) && a.getColonne()==(colonne+1))sum++;
            if(a.getLigne()==(ligne+1) && a.getColonne()==(colonne))sum++;
            if(a.getLigne()==(ligne) && a.getColonne()==(colonne+1))sum++;
            if(a.getLigne()==(ligne) && a.getColonne()==(colonne-1))sum++;
            a=a.getSuiv();
        }
        return sum;
    }

    public void removeMaillon(int ligne,int colonne){

    }

    public int getLongueurColonne(){
        return longueurColonne;
    }

    public int getLongueurLigne(){
        return longueurLigne;
    }

    public static List projeter(List grille, Couple couple) {
        List l = new List();
        Maillon a = grille.tete;

        while(a!=null){
            l.addMaillon(new Maillon(a.getLigne()+couple.getLigne(),a.getColonne()+couple.getColonne(),1));
            a=a.getSuiv();
        }
        return l;
    }

    public List eliminerNonConformes() {
        Maillon a = tete;
        List ng = new List();
        while(a!=null){
            if(a.getNbvois()==3 || a.getNbvois()==1002 || a.getNbvois()==1003)
                ng.addMaillon(new Maillon(a.getLigne(),a.getColonne(),1));
            a=a.getSuiv();
        }
        return ng;
    }
}
