public class Maillon implements Comparable{

    private int ligne;
    private int colonne;
    private int nbvois;
    private Maillon suiv;


    /*
    public Maillon(Couple i){
        this.i = i;
        suiv = null;
    }
    */
    public Maillon(int ligne, int colonne,int nbvois){
        this.ligne = ligne;
        this.nbvois=nbvois;
        this.colonne = colonne;
        suiv = null;
    }



    public int getLigne(){
        return ligne;
    }

    public int getColonne(){
        return colonne;
    }

    public void setSuivant(Maillon i){
        this.suiv = i;
    }

    public int compareTo(Object o){
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return -1;
        Maillon other = (Maillon) o;
        if(other.getLigne() < this.ligne)return 1;
        if(ligne == other.getLigne()) {
            if (other.getColonne() < colonne)
                return 1;
            else
                return (other.getColonne() == colonne ? 0 : -1);
        }
        return -1;
    }

    public Maillon getSuiv() {
        return suiv;
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
