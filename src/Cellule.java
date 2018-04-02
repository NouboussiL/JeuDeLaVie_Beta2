public class Cellule implements Comparable{

    private int ligne;
    private int colonne;
    private int nbvois;



    public Cellule(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
        this.nbvois = 1;
    }

    public Cellule(int ligne, int colonne,int nbVoisins){
        this(ligne,colonne);
        this.nbvois = nbVoisins;
    }


    public int getLigne(){
        return ligne;
    }


    public int getColonne(){
        return colonne;
    }




    public int compareTo(Object o){
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return -1;
        Cellule other = (Cellule) o;
        if(other.getLigne() < this.ligne)return 1;
        if(ligne == other.getLigne()) {
            if (other.getColonne() < colonne)
                return 1;
            else
                return (other.getColonne() == colonne ? 0 : -1);
        }
        return -1;
    }


    @Override
    public String toString(){
        return "("+this.ligne+","+this.colonne+","+this.nbvois+")";
    }

    public int getNbvois() {
        return nbvois;
    }

    public void setNbvois(int nbvois) {
        this.nbvois = nbvois;
    }
}
