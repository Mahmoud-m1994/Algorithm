class Kø{
    private Object[] tab;
    private int start=0;
    private int slutt=0;
    private int antall=0;

    public Kø(int str){
        tab=new Object[str];
    }

    public boolean tom(){
        return antall==0;
    }
    public boolean full(){
        return  antall==tab.length;
    }
    public void leggIKø(Object e){
        if(full()) return;
        tab[slutt]=e;
        slutt=(slutt+1)%tab.length;
        ++antall;
    }
    public Object nesteIKø(){
        if(!tom()){
            Object e =tab[start];
            start=(start+1)%tab.length;
            --antall;
            return e;
        }
        else{
            return null;
        }
    }
    public Object sjekkKø(){
        if(!tom())return tab[start];
        else return null;
    }
}