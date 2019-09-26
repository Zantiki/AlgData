package Øving_1;

import Gjenbruk.Resultat;

import java.util.Date;
import java.util.Arrays;
import java.util.Random;

public class Aksjedata {


    public int[] algoritme(int[] testdata){
        int verdi = 10;
        int avkastning = 0; //salg-kjøp
        int antallDager = testdata.length;
        int[] verdier = new int[antallDager];
        int[] kjøpSalg = new int[2];

        for(int i = 0; i < antallDager; i++){
            verdi += testdata[i];
            verdier[i] = verdi;
        }
        verdi = 10;

        for(int i = 0; i < antallDager; i++) {
            for (int y = i; y < antallDager; y++) {
                if((verdier[y] - verdier[i]) > avkastning){
                    avkastning = verdier[y] - verdier[i];
                    //Plusser på en pga arrayJustering
                    kjøpSalg[0] = i+1;
                    kjøpSalg[1] = y+1;
                }
            }
        }
        return kjøpSalg;
    }

    public int[] genererData(int n){
        Random rnd  = new Random();
        int[] data = new int[n];
        for(int i = 0; i < n; i++) {
            data[i] = rnd.nextInt((100 + 10) + 1) - 10;
        }
        return data;
    }

    public void mikroOperasjonBenchMark(){
        int i = 3;
    }

    public void skrivTilCSV(int[] settStørrelse, double[] tider){
        String filnavn = this.getClass().getName()+".CSV";
        new Resultat(settStørrelse, tider, filnavn);
    }


  public static void main(String[] args){
      try {
          Aksjedata testdata = new Aksjedata();
          int[] settStørrelser = {10000, 15000, 20000, 25000};
          double[] tider = new double[settStørrelser.length];

          //Tester om algoritmen returnerer riktig resultat for oppgavedataen.
          int[] kurser = {-1, 3, -9, 2, 2, -1, 2, -1, -5};
          int[] riktig = {3, 7};
          int[] test = testdata.algoritme(kurser);
          if (!Arrays.equals(test, riktig)) {
              throw new Exception();
          }

          //Regner ut gjennomsnittstid på x antall operasjoner i løpet av en sek.
          for (int i = 0; i < settStørrelser.length; i++) {
              int runder = 0;
              double tid;
              kurser = testdata.genererData(settStørrelser[i]);
              Date slutt;
              Date start = new Date();
              do {
                  int[] r = testdata.algoritme(kurser);
                  slutt = new Date();
                  runder++;
              } while (slutt.getTime() - start.getTime() < 1000);
              tid = (double) (slutt.getTime() - start.getTime()) / runder;
              tider[i] = tid;
              System.out.println("Settstørrelse: " + settStørrelser[i] + ", Millisekund pr. runde:" + tid);
          }
          //Skriver dataen til CSV som kan pånes i excel
          testdata.skrivTilCSV(settStørrelser, tider);

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

    }catch(Exception e){
        System.out.println("!!!METODEFEIL!!!:Metoderesultat er ikke riktig");
    }

  }
}
