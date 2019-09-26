package Øving_5;

import Gjenbruk.Resultat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Utility {


    public Integer[] genData(int settStørrelse){
        Random rnd = new Random();
        //Integer[] data = new Integer[settStørrelse];
        int prime = 3000000;
        Integer[] data = new Integer[prime];

        for(int i = 0; i < prime; i++){
            data[i] = (Integer) rnd.nextInt(100000000 - 1000000) +1000000;
        }
        return data;
    }

    public boolean fillHash(Integer[] data, HashMap<Integer,Integer> map) {
        for(int i = 0; i < data.length; i++){
            map.put(data[i], null);
        }
        return true;
    }

    public boolean fillTable(Integer[] data, Integer[] table){
        for(int i = 0; i < data.length; i++ ){
            data[i] = table[i];
        }
        return true;
    }

    public void skrivAlgoTilCSV(int[] settStørrelse, double[] tider, String addon){
        String filnavn = this.getClass().getName()+addon+".CSV";
        new Resultat(settStørrelse, tider, filnavn);
    }

    public static void main(String[] args) {
        try {
            int antElementer = 5000000+49997;
            Heltallshash test = new Heltallshash(antElementer, false, false);
            Integer[] testArray = new Integer[antElementer];
            HashMap<Integer, Integer> testNative = new HashMap<Integer, Integer>();
            Utility util = new Utility();
            int[] settStørrelser = {1, 2, 3};
            double[] tider = new double[3];
            Integer[] data = util.genData(antElementer);
            int e;
            double collision = 0;

            //Tester om algoritmen returnerer riktig resultat for oppgavedataen.
            System.out.println("Starter test");

            //Regner ut gjennomsnittstid på x antall operasjoner i løpet av en sek.
            //for (int i = 0; i < settStørrelser.length; i++) {
            int runder = 0;
            double tid;
            Date slutt;
            Date start = new Date();
            do {
                e = test.fillTable(data);
                slutt = new Date();
                collision = test.getCollision();
                test = new Heltallshash(antElementer, false, false);
                runder++;
            } while (slutt.getTime() - start.getTime() < 4000);
            tid = (double) (slutt.getTime() - start.getTime()) / runder;
            tider[0] = tid;
            System.out.println("Hashtable: Settstørrelse: " + antElementer + ", Millisekund pr. runde:" + tid+", Elementer "+e);
            System.out.println("Gjennomsnittskollisjoner: "+collision);
            //}
            //Skriver dataen til CSV som kan åpnes i excel
            util.skrivAlgoTilCSV(settStørrelser, tider, "hash");

            // for (int i = 0; i < settStørrelser.length; i++) {
            runder = 0;
            start = new Date();
            do {
                util.fillTable(data, testArray);
                slutt = new Date();
                testArray = new Integer[antElementer];
                runder++;
            } while (slutt.getTime() - start.getTime() < 1000);
            tid = (double) (slutt.getTime() - start.getTime()) / runder;
            tider[1] = tid;
            System.out.println("Vanlig Tabell: Settstørrelse: " + antElementer + ", Millisekund pr. runde:" + tid);
            // }

            runder = 0;
            start = new Date();
            do {
                util.fillHash(data, testNative);
                slutt = new Date();
                testNative = new HashMap<>();
                runder++;
            } while (slutt.getTime() - start.getTime() < 1000);
            tid = (double) (slutt.getTime() - start.getTime()) / runder;
            tider[2] = tid;
            System.out.println("JavaNative: Settstørrelse: " + antElementer + ", Millisekund pr. runde:" + tid);
            //Skriver dataen til CSV som kan pånes i excel
            util.skrivAlgoTilCSV(settStørrelser, tider, "JavaNative");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
