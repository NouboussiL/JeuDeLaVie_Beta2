public class List {

    Maillon tete;
    int longueurColonne;
    int longueurLigne;

    public List(){
        tete = null;
    }

    public void addMaillon(Maillon maillon){
        if(tete == null){
            tete = maillon;
        }
        else{
            if(maillon.compareTo(tete)==-1){
                maillon.setSuivant(tete);
                tete = maillon;
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
                }
            }

        }
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
}
