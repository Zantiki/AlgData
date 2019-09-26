package Gjenbruk;

import java.io.File;
import java.io.PrintWriter;
import java.sql.ResultSet;

public class Resultat {
    int[] settStørrelser;
    int[] intervallStørrelser;
    double[] tider;
    String filnavn;

    public Resultat(int[] settStørrelser, double[] tider, String filnavn){
        this.settStørrelser = settStørrelser;
        this.tider = tider;
        this.filnavn = filnavn;
        skrivTilCSV();
    }

    public String toString(int n, double tid){
        return n+", " +tid+" \n";
    }

    private void skrivTilCSV(){
        try{
            PrintWriter writer = new PrintWriter(filnavn);
            for (int i = 0; i < tider.length; i++){
                writer.write(this.toString(settStørrelser[i], tider[i]));
            }
            writer.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
