package Øving_4;

public class Stakk {
    private Object[] tab;
    private int antall = 0;

    public Stakk(int str){
        tab = new Object[str];
    }

    public boolean tom(){
        return antall == 0;
    }

    public boolean full(){
        return antall == tab.length;
    }

    public void push(Object e){
        if(!full()){
            tab[antall++] = e;
        }
    }

    public Object pop(){
        if(!tom())return tab[--antall];
        else return null;
    }

    public Object sjekkStakk(){
        if(!tom()) return tab[antall-1];
        else return null;
    }
}
