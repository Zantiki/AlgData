package Øving_7;

public class Quene {
    private Object[] tab;
    private int start = 0;
    private int stop = 0;
    private int amount = 0;

    public Quene(int size){
        tab = new Object[size];
    }
    public boolean empty(){
        return amount == 0;
    }

    public boolean full(){
        return amount == tab.length;
    }

    public void addToQuene(Object o){
        if(full()) return;
        tab[stop] = o;
        stop = (stop+1)%tab.length;
        amount++;
    }

    public Object nextInQuene(){
        if(!empty()){
            Object o = tab[start];
            start = (start+1)%tab.length;
            amount--;
            return o;
        }
        else return null;
    }

    public Object checkQuene(){
        if(!empty()) return tab[start];
        else return null;
    }
}
