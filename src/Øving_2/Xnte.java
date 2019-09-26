package Øving_2;

import Gjenbruk.Resultat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Xnte {
    public double algoritme(int n,  double x){
        if(n == 0){
            return 1.00;
        }else{
            return x * algoritme(n-1, x);
        }
    }

    public double algo2(int n, double x){
        if(n == 0){
            return 1;
        }else if((n % 2) != 0){
            return x*algo2((n-1)/2,x*x );
        }else{
            return algo2(n/2, x*x);
        }
    }

    public double javaAlgo(int n,  double x){
        return Math.pow(x, Double.valueOf(n));
    }

   /* public int[] genererData(int n){
        Random rnd  = new Random();
        double[][] data = new double[n][2];
        for(int i = 0; i < n; i++) {
            data[i][0] = rnd.nextInt((100 + 10) + 1) - 10;
            data[i][1] = rnd.nextInt((100 + 10) + 1) - 10;
        }
        return data;
    }*/

    public void mikroOperasjonBenchMark(){
        int i = 3;
    }

    public void skrivAlgoTilCSV(int[] settStørrelse, double[] tider){
        String filnavn = this.getClass().getName()+".CSV";
        new Resultat(settStørrelse, tider, filnavn);
    }

    public void skrivAlgo2TilCSV(int[] settStørrelse, double[] tider){
        String filnavn = this.getClass().getName()+"2.CSV";
        new Resultat(settStørrelse, tider, filnavn);
    }

    public void skrivJavaAlgoTilCSV(int[] settStørrelse, double[] tider){
        String filnavn = this.getClass().getName()+"java.CSV";
        new Resultat(settStørrelse, tider, filnavn);
    }

    public boolean test(){
        //tester om algoritene returnenerer riktig svar
        Random rnd = new Random();
        int[] ner = {5, 7, 8, 11};
        Double[] xer = {2.00,3.23,7.08,43.5};

        for(int i = 0; i < xer.length; i++){
            int n = ner[rnd.nextInt(ner.length)];
            double x = xer[rnd.nextInt(xer.length)];

            if(Math.round(Math.pow(x, Double.valueOf(n))) != Math.round(this.algoritme(n, x))){
                System.out.println(Math.pow(x, Double.valueOf(n))+" vs. "+this.algoritme(n, x));
                return false;
            }
            if(Math.round(Math.pow(x, Double.valueOf(n))) != Math.round(this.algo2(n, x))){
                System.out.println(Math.pow(x, Double.valueOf(n))+" vs. "+this.algo2(n, x));
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            Xnte testdata = new Xnte();
            int[] settStørrelser = {1000, 5000, 10000, 15000,};
            double[] tider = new double[settStørrelser.length];
            double x = 1.00001;
            double r;


            //Tester om algoritmen returnerer riktig resultat for oppgavedataen.
            if(!testdata.test()){
                System.out.println("Algoritmefeil");
                return;
            }

            //Regner ut gjennomsnittstid på x antall operasjoner i løpet av en sek.
            for (int i = 0; i < settStørrelser.length; i++) {
                int runder = 0;
                double tid;
                Date slutt;
                Date start = new Date();
                do {
                    r = testdata.algoritme(settStørrelser[i], x);
                    slutt = new Date();
                    runder++;
                } while (slutt.getTime() - start.getTime() < 1000);
                tid = (double) (slutt.getTime() - start.getTime()) / runder;
                tider[i] = tid;
                System.out.println("Settstørrelse: " + settStørrelser[i] + ", Millisekund pr. runde:" + tid);
            }
            //Skriver dataen til CSV som kan åpnes i excel
            testdata.skrivAlgoTilCSV(settStørrelser, tider);

            for (int i = 0; i < settStørrelser.length; i++) {
                int runder = 0;
                double tid;
                Date slutt;
                Date start = new Date();
                do {
                    r = testdata.algo2(settStørrelser[i], x);
                    slutt = new Date();
                    runder++;
                } while (slutt.getTime() - start.getTime() < 1000);
                tid = (double) (slutt.getTime() - start.getTime()) / runder;
                tider[i] = tid;
                System.out.println("Settstørrelse: " + settStørrelser[i] + ", Millisekund pr. runde:" + tid);
            }
            //Skriver dataen til CSV som kan pånes i excel
            testdata.skrivAlgo2TilCSV(settStørrelser, tider);

            for (int i = 0; i < settStørrelser.length; i++) {
                int runder = 0;
                double tid;
                Date slutt;
                Date start = new Date();
                do {
                    r = testdata.javaAlgo(settStørrelser[i], x);
                    slutt = new Date();
                    runder++;
                } while (slutt.getTime() - start.getTime() < 1000);
                tid = (double) (slutt.getTime() - start.getTime()) / runder;
                tider[i] = tid;
                System.out.println("Settstørrelse: " + settStørrelser[i] + ", Millisekund pr. runde:" + tid);
            }
            //Skriver dataen til CSV som kan pånes i excel
            testdata.skrivJavaAlgoTilCSV(settStørrelser, tider);

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
            System.out.println("Feil ved skriving til CSV");
        }

    }

}
