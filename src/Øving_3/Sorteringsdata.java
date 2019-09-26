package Øving_3;

import Gjenbruk.Resultat;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Sorteringsdata {

    public void quickSort(int[] n, int v, int h){
        if((h-v) > 2){
            int splitIndex = split(n,v, h);
            quickSort(n, v, splitIndex-1);
            quickSort(n, splitIndex + 1, h);
        } else{
            median3Sort(n, v, h);
        }
    }

    public int median3Sort(int[] n,int v,int h){
        int m = (v+h)/2;
        if (n[v] > n[m]) {
            int temp = n[m];
            n[m] = n[v];
            n[v] = temp;
        }
        if (n[m] > n[h]) {
            int temp = n[h];
            n[h] = n[m];
            n[m] = temp;
            if (n[v] > n[m]) {
                temp = n[m];
                n[m] = n[v];
                n[v] = temp;
            }
        }
        return m;
    }

    public void swap(int[] n, int i, int j){
        int temp = n[j];
        n[j] = n[i];
        n[i] = temp;
    }

    public int split(int[] n, int v, int h){
        int iv;
        int ih;
        int m = median3Sort(n,v,h);
        int dv = n[m];
        int temp = n[h-1];
        n[h-1] = n[m];
        n[m] = temp;
        for(iv = v, ih = h-1;;) {
            while (n[++iv] < dv) ;
            while (n[--ih] > dv) ;
            if (iv >= ih){
                break;
            }
            temp = n[ih];
            n[ih] = n[iv];
            n[iv] = temp;
        }
        temp = n[h-1];
        n[h-1] = n[iv];
        n[iv] = temp;
        return iv;
    }

    public void quickerSort(int[] n, int v, int h){
        if(v > 0 && h < n.length-1 && n[v-1] == n[h+1]){return;}
        if((h-v) > 2){
            int splitIndex = split(n,v, h);
            quickSort(n, v, splitIndex-1);
            quickSort(n, splitIndex + 1, h);
        } else{
            median3Sort(n, v, h);
        }
    }

    //ved å sett duplicable true får man en tabell med mange duplikater.
    public int[] genererData(int n, boolean duplicable){
        Random rnd  = new Random();
        int[] data = new int[n];
        if(!duplicable) {
            for (int i = 0; i < n; i++) {
                data[i] = rnd.nextInt(100);
            }
        }else{
            int duplicator = rnd.nextInt(100);
            int totDuplicates = (int)Math.round(n*0.25);
            boolean dupeTurn = false;
            int dupeRounds = 0;
            //Ca 25% av tabellen blir dublikater.
            for (int i = 0; i < n; i++ ){
                if(dupeTurn && dupeRounds <= totDuplicates){
                    data[i] = duplicator;
                    dupeRounds++;
                }
                data[i] = rnd.nextInt(100);
                dupeTurn = rnd.nextBoolean();
            }

        }
        return data;
    }

    public void mikroOperasjonBenchMark(){
        int i = 3;
    }

    public void skrivAlgoTilCSV(int[] settStørrelse, double[] tider){
        String filnavn = this.getClass().getName()+".CSV";
        new Resultat(settStørrelse, tider, filnavn);
    }

    public void skrivAlgo2TilCSV(int[] settStørrelse, double[] tider, String addon){
        String filnavn = this.getClass().getName()+addon+".CSV";
        new Resultat(settStørrelse, tider, filnavn);
    }




    public boolean test(){
        int[] bigMess = {3, 5, 6, 4, 2, 9, 1, 7};
        int[] bigMess2 = {4, 5, 7, 3, 1, 6, 2, 9};;
        quickSort(bigMess2, 0, bigMess2.length-1);
        Arrays.sort(bigMess);
        if(Arrays.equals(bigMess2, bigMess)){
            return true;
        }else{
            System.out.println(Arrays.toString(bigMess2));
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            Sorteringsdata testdata = new Sorteringsdata();
            int[] settStørrelser = {250000, 500000, 750000, 1000000};
            double[] tider = new double[settStørrelser.length];
            int[] data;

            //Tester om algoritmen returnerer riktig resultat for oppgavedataen.
            System.out.println("Starter test");
            if(!testdata.test()){
                System.out.println("Algoritmefeil");
                return;
            }
            System.out.println("Test bestått");

            //Regner ut gjennomsnittstid på x antall operasjoner i løpet av en sek.
            for (int i = 0; i < settStørrelser.length; i++) {
                data = testdata.genererData(settStørrelser[i], false);
                int runder = 0;
                double tid;
                Date slutt;
                Date start = new Date();
                do {
                    testdata.quickSort(data, 0, data.length-1);
                    slutt = new Date();
                    runder++;
                    //Resetter dataen
                    data = testdata.genererData(settStørrelser[i], false);
                } while (slutt.getTime() - start.getTime() < 1000);
                tid = (double) (slutt.getTime() - start.getTime()) / runder;
                tider[i] = tid;
                System.out.println("QUICK: Settstørrelse: " + settStørrelser[i] + ", Millisekund pr. runde:" + tid);
            }
            //Skriver dataen til CSV som kan åpnes i excel
            testdata.skrivAlgoTilCSV(settStørrelser, tider);

            for (int i = 0; i < settStørrelser.length; i++) {
                data = testdata.genererData(settStørrelser[i], false);
                int runder = 0;
                double tid;
                Date slutt;
                Date start = new Date();
                do {
                    testdata.quickerSort(data, 0, data.length-1);
                    slutt = new Date();
                    runder++;
                } while (slutt.getTime() - start.getTime() < 1000);
                tid = (double) (slutt.getTime() - start.getTime()) / runder;
                tider[i] = tid;
                System.out.println("QUICKER: Settstørrelse: " + settStørrelser[i] + ", Millisekund pr. runde:" + tid);
            }
            //Skriver dataen til CSV som kan pånes i excel
            testdata.skrivAlgo2TilCSV(settStørrelser, tider, "Quicker");

            for (int i = 0; i < settStørrelser.length; i++) {
                data = testdata.genererData(settStørrelser[i], true);
                int runder = 0;
                double tid;
                Date slutt;
                Date start = new Date();
                do {
                    testdata.quickSort(data, 0, data.length-1);
                    slutt = new Date();
                    runder++;
                } while (slutt.getTime() - start.getTime() < 1000);
                tid = (double) (slutt.getTime() - start.getTime()) / runder;
                tider[i] = tid;
                System.out.println("QUICKDUPE: Settstørrelse: " + settStørrelser[i] + ", Millisekund pr. runde:" + tid);
            }
            testdata.skrivAlgo2TilCSV(settStørrelser, tider, "QDupe");

            for (int i = 0; i < settStørrelser.length; i++) {
                data = testdata.genererData(settStørrelser[i], true);
                int runder = 0;
                double tid;
                Date slutt;
                Date start = new Date();
                do {
                    testdata.quickerSort(data, 0, data.length-1);
                    slutt = new Date();
                    runder++;
                } while (slutt.getTime() - start.getTime() < 1000);
                tid = (double) (slutt.getTime() - start.getTime()) / runder;
                tider[i] = tid;
                System.out.println("QUICKERDUPE: Settstørrelse: " + settStørrelser[i] + ", Millisekund pr. runde:" + tid);
            }
            testdata.skrivAlgo2TilCSV(settStørrelser, tider, "QukerDupe");


            //Forsøk på å finne gjennomsnittstiden på en mikrooperasjon.
            int runder = 0;
            double tid;
            Date slutt;
            Date start = new Date();

            do {
                testdata.mikroOperasjonBenchMark();
                slutt = new Date();
                runder++;
            } while (slutt.getTime() - start.getTime() < 1000);
            tid = (double) (slutt.getTime() - start.getTime()) / runder;
            System.out.println("Millisekund pr. mikrooperasjon: " + tid);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Feil ved skriving til CSV");
        }

    }

}