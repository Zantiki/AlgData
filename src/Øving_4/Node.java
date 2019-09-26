package Øving_4;

public class Node {
    Object element;
    Node neste;
    Node forrige;

    public Node(Object e, Node n, Node f){
        element = e;
        neste = n;
        forrige = f;
    }
    public Object finnElement(){
        return element;
    }
    public Node finnNeste(){
        return neste;
    }

    public Node finnForrige(){
        return forrige;
    }
}
