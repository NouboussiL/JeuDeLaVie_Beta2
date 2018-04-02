public class List<T extends Comparable<T>> {

    private Maillon<T> tete;

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



    public Maillon<T> getTete(){
        return tete;
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

}
