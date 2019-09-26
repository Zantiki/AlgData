package Øving_4;

public class Lenke {
    private Node hode = null;
    private int antElementer = 1;
    public int finnAntall(){return antElementer;}
    public Node finnHode(){return hode;}
    public Node hale = null;

    public void settInnFremst(String verdi){
        hode = new Node(verdi, hode, null);
        if(hale == null) hale = hode;
        else hode.neste.forrige = hode;
        hode.forrige = hale;
        ++antElementer;
    }

   public void settInnBakerst(Object verdi){
        Node ny = new Node(verdi, hode, hale);
        if(hale != null) hale.neste = ny;
        else hode = ny;
        hale = ny;
        ny.neste = hode;
        hode.forrige = ny;
        //hale.forrige = hode;
       //System.out.println(hode);
       //System.out.println(hale);
        ++antElementer;
    }

    /*public String[] getAll(){
        while(counter < antElementer)
    }*/

   /* public void settInnBakerst(int verdi){
        if(hode == null){
            hode = new Node(verdi, null, null);
            hale = new Node(verdi, hode, hode);
            hode.neste = hale;
            hode.forrige = hale;
        }
        Node ny = new Node(verdi, hale, null);
        if(hode.neste == hale){
            hode.neste = ny;
        }else{
            hale.neste = ny;
            ny.neste = hode;
        }
        ++antElementer;
    }*/

    public Node fjern(Node n){
        if(n.forrige != null)
            n.forrige.neste = n.neste;
         else hode = n.neste;
         if(n.neste != null)
            n.neste.forrige = n.forrige;
         else hale = n.forrige;
        n.neste = null;
        n.forrige = null;
        //hale.forrige = hode;
        --antElementer;
        return n;
    }

}
