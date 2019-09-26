package Øving_4.Testprogrammer;

import Øving_4.Lenke;
import Øving_4.Selvmordsliste;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            Selvmordsliste testdata = new Selvmordsliste();
            int[] settStørrelser = {1000, 5000, 10000, 15000};
            int[] intervallStørrelser = {100, 500, 1000, 1500};
            double[] tider = new double[settStørrelser.length];
            int r;

            //Tester om algoritmen returnerer riktig resultat for oppgavedataen.
            /*if(!testdata.test()){
                System.out.println("Algoritmefeil");
                return;
            }*/

            //Regner ut gjennomsnittstid på x antall operasjoner i løpet av en sek.
            for (int i = 0; i < settStørrelser.length; i++) {
                int runder = 0;
                double tid;
                Lenke l = testdata.genererData(settStørrelser[i]);
                Date slutt;
                Date start = new Date();
                do {
                    r = testdata.algoritme(l, intervallStørrelser[i]);
                    slutt = new Date();
                    runder++;
                    l = testdata.genererData(settStørrelser[i]);
                } while (slutt.getTime() - start.getTime() < 1000);
                tid = (double) (slutt.getTime() - start.getTime()) / runder;
                tider[i] = tid;
                System.out.println("Settstørrelse: " + settStørrelser[i] + ", Millisekund pr. runde:" + tid);
            }
            //Skriver dataen til CSV som kan åpnes i excel
            testdata.skrivAlgoTilCSV(settStørrelser, intervallStørrelser, tider, "");

            //Forsøk på å finne gjennomsnittstiden på en mikrooperasjon.
            int runder = 0;
            double tid;
            Date slutt;
            Date start = new Date();

            do {
                testdata.mikroOperasjonBenchMark();
                slutt = new Date();
                runder++;
                ) while (slutt.getTime() - start.getTime() < 1000);
            tid = (double) (slutt.getTime() - start.getTime()) / runder;
            System.out.println("Millisekund pr. mikrooperasjon: " + tid);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Feil ved skriving til CSV");
        }

    }
}


