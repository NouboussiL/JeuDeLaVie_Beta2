public class Maillon<T> implements Comparable{

    private T info;
    private Maillon suiv;


    public Maillon(){
        info = null;
        suiv = null;
    }

    /*
    public Maillon(Couple i){
        this.i = i;
        suiv = null;
    }
    */
    public Maillon(T info){
        this.info = info;
        suiv = null;
    }


    public T getInfo(){
        return this.info;
    }


    public void setSuivant(Maillon i){
        this.suiv = i;
    }

    public int compareTo(Object o){
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return -1;
        Maillon other = (Maillon)o;
        return info.compareTo(other.getInfo());
    }

    public Maillon getSuiv() {
        return suiv;
    }

    @Override
    public String toString(){
        return info.toString();
    }

}
