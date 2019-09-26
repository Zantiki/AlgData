package Øving_4;

import Gjenbruk.IntervallResultat;
import java.util.ListIterator;

import java.util.*;

public class Selvmordsliste {

    /*public int algoritme(LinkedList<Integer> suiCircle, int interval){
        int i = 0;
        while(suiCircle.size() > 1){
            if(suiCircle.size()-1 < interval){
                while(suiCircle.size()-1 < interval){
                    interval--;
                }
                System.out.println("Intevallstørrelse: "+interval);
            }
            i+=interval;
            System.out.println(suiCircle.toString());
            System.out.println(suiCircle.get(adjustI(i, suiCircle.size()-1, interval))+" Tar selvmord...");
            suiCircle.remove(adjustI(i, suiCircle.size()-1, interval));
        }
        return suiCircle.getLast();
    }*/

    public int algoritme(Lenke l, int interval){
        int interWalk = 0;
        Node posisjon = l.finnHode();
        //System.out.println("Tester hoderefereanse");
        //System.out.println(l.finnHode().finnForrige());
        while(l.finnAntall() > 1){
            while(interWalk < interval+1){
                interWalk++;
                posisjon = posisjon.finnNeste();
            }
            /*System.out.println("Antall igjen: "+l.finnAntall()+" | Nåværende posisjon: "+posisjon.finnElement());
            System.out.println(l.fjern(posisjon.finnForrige()).finnElement()+" fjernet fra liste");*/
            l.fjern(posisjon.finnForrige());

            interWalk = 0;
        }
       return (Integer) posisjon.finnElement();
    }

   /* public int algoritme2(LinkedList<Integer> suiCircle, int interval) {
        int interWalk = 0;
        ListIterator<Integer> iter = suiCircle.listIterator(0);
        Integer selectedElement = 0;
        int rawIndex = 0;
        while(suiCircle.size() > 1){

            interWalk = 0;
            while(interWalk <= interval && iter.hasNext()){
                //selectedIndex = iter.nextIndex();
                selectedElement = iter.next();
                //System.out.println(iter.nextIndex());
                interWalk++;
                rawIndex++;
            }
            //System.out.println(iter.toString());
            /*if(rawIndex > (suiCircle.size()-1)){
                iter = suiCircle.listIterator(rawIndex-(rawIndex /(suiCircle.size()-1)*(suiCircle.size()-1)));
            }
            try{
                selectedElement = iter.next();
                iter.remove();
                System.out.println(selectedElement+" Tar selvmord...");
            }catch(Exception e){
                rawIndex = 0;
                iter = suiCircle.listIterator(0);
                selectedElement = iter.next();
                iter.remove();
                System.out.println(selectedElement+" Tar selvmord...");

            }
            System.out.println(suiCircle.toString());
        }
        return suiCircle.getFirst();
    }

    public int adjustI(int curIndex, int maxIndex, int interval){
        int rounds = 1;
        if(curIndex/maxIndex > 1){
            rounds = (curIndex/maxIndex)+1;
        }
        int unAdjusted = curIndex+interval;
        if(unAdjusted >= maxIndex){
            System.out.println("Indeks justert, kjørt "+rounds+" runder "+ (unAdjusted-(rounds*maxIndex)));
            return unAdjusted-maxIndex;
        }else{
            System.out.println("Indeks ujustert: "+unAdjusted);
            return unAdjusted;
        }

    }*/


    public Lenke genererData(int n){
        Lenke data = new Lenke();
        for(int i = 0; i < n; i++){
            data.settInnBakerst(i);
        }
        //System.out.println("Listen har "+data.finnAntall()+" Elementer, hvor hvert element korresponderer til sin start-indeks");
        return data;
    }

    public void mikroOperasjonBenchMark(){
            int i = 3;
        }

    public void skrivAlgoTilCSV(int[] settStørrelse, int[] intervaller, double[] tider, String addon){
        String filnavn = this.getClass().getName()+addon+".CSV";
        new IntervallResultat(settStørrelse, intervaller, tider, filnavn);
    }

    public boolean test(){
        /*Random rnd = new Random();
        int dataSize = rnd.nextInt(100-3)+1;
        int interval = rnd.nextInt(dataSize-2)+2;*/
        //System.out.println("Intervallstørrelsen er: "+interval);
        //Lenke testData = genererData(dataSize);
        Lenke testJosephus = genererData(40);
        //int res = algoritme3(testData, interval);
        int res = algoritme(testJosephus, 2);
        System.out.println("Tyggeste plassen: "+res);
        return res == 31;
    }

    public static void main(String[] args){
        Selvmordsliste test = new Selvmordsliste();
        test.test();
    }

}
