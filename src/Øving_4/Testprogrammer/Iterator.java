package Øving_4.Testprogrammer;

import Øving_4.Lenke;
import Øving_4.Node;

public class Iterator {
    private Node plass;
    public Iterator(Lenke l){
        plass = l.finnHode();
    }
    public boolean slutt(){
        return plass == null;
    }
    public double finnElement(]){
        if(!slutt()){
            return plass.finnElement();
        }else{
            return Double.NaN;
        }
    }
    public void neste(){
        if(!slutt()){
            plass = plass.finnNeste();
        }
    }
    public Node getPlass(){
        return plass;
    }
}
