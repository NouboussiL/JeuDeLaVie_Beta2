public class Maillon<T extends Comparable<T>> implements Comparable{

    private  T info;
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
        T infoOther = (T)other.getInfo();
        return info.compareTo(infoOther);
    }

    public Maillon getSuiv() {
        return suiv;
    }

    @Override
    public String toString(){
        return info.toString();
    }

}
