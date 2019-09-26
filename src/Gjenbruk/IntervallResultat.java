package Gjenbruk;

import java.io.PrintWriter;

public class IntervallResultat extends Resultat {
    int[] intervaller;
    public IntervallResultat(int[] settStørrelser, int[] intervaller, double[] tider, String filnavn){
        super(settStørrelser, tider, filnavn);
        this.intervaller = intervaller;
    }

    public String toString(int n, int intervall, double tid){
        return n+", "+intervall+", " +tid+" \n";
    }

    private void skrivTilCSV(){
        String skrivNavn = "\\CSV\\" + filnavn;
        try{
            PrintWriter writer = new PrintWriter(skrivNavn);
            for (int i = 0; i < tider.length; i++){
                writer.write(this.toString(settStørrelser[i], intervaller[i], tider[i]));
            }
            writer.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
