package Øving_5;
import Øving_4.Lenke;
import Øving_4.Node;

import java.io.BufferedReader;
import java.io.FileReader;

public class Hashtable {
    String filnavn;
    Lenke[] table = null;
    int indeces = 0;
    int elements = 0;
    int collisions = 0;

    public Hashtable(String filnavn){
        this.filnavn = filnavn;
        readData();
    }

    public double getAvgCollisions(){
       return (double)collisions/(double)elements;
    }

    public void readData(){
        try{
            int size = 0;
            FileReader con = new FileReader(filnavn);
            BufferedReader leser = new BufferedReader(con);
            String linje = "";
            while(leser.readLine() != null){
                size++;
            }
            size/=4;
            table = new Lenke[size];
            indeces = size;
            leser.close();
            for(int i = 0; i < table.length; i++){
                table[i] = new Lenke();
            }

            con = new FileReader(filnavn);
            leser = new BufferedReader(con);
            String formatName = "";
            while(linje != null){
                formatName = "";
                linje = leser.readLine();
                if(linje != null){
                    String format[] = linje.split(",");
                    for (int i = 0; i < format.length; i++) {
                        formatName += format[i] + " ";
                    }
                    insertElement(formatName.toCharArray());
                }
                //System.out.println(formatName);
            }
            leser.close();
            con.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getSize(){
        return indeces;
    }

    public int getElements(){
        return elements;
    }

    public void insertElement(char[] navn){
        elements++;
        int chainIndex = hash(navn);
        if(table[chainIndex].finnHode() != null){
            System.out.println(table[chainIndex].finnHode().finnElement()+"kolliderer med "
                    +new String(navn)+" på nøkkel: "+chainIndex);
            collisions += table[chainIndex].finnAntall();
        }else{
            System.out.println(new String(navn)+"lest inn med nøkkelverdi:"+chainIndex);
        }
        table[chainIndex].settInnFremst(new String(navn));
    }

    public String find(String name){
        int index = hash(name.toCharArray());

        if(indeces < index){
            return null;
        }

        if(table[index].finnHode() == null){
            return null;

        }else{
            Node current = table[index].finnHode();
            int counter = 0;
            while(current != null && counter < table[index].finnAntall()){
                current = current.finnNeste();
                if(current != null && current.finnElement().equals(name)){
                    return name;
                }
                counter++;
                System.out.print(current.finnElement()+ " kolliderer med "+name);
            }
        }
        return null;
    }

    public String[] getAll(){
        String[] allNames = new String[elements];
        int chainCounter = 0;
        int tableCounter = 0;
        Node current =  null;
        for(int i = 0; i < table.length; i++){
            chainCounter= 1;
            current = table[i].finnHode();
            if(current != null){
               while(table[i].finnAntall() > chainCounter) {
                   allNames[tableCounter] = (String) current.finnElement();
                   //System.out.println("Nøkkel: "+i+"  Listeindex: "+chainCounter);
                   current = current.finnNeste();
                   chainCounter++;
                   tableCounter++;
               }
           }
        }
        return allNames;
    }

    public int hash(char[] navn){
        int key = 0;
        for(int i = 0; i < navn.length; i++){
            key += ((i+1)*(int)navn[i]);
        }
        return key%indeces;
    }

    public double loadFactor(){
        int m = 0;
        for(int i = 0; i < table.length; i++){
            m += table[i].finnAntall();
        }
        return (double)elements/m;
    }


    public static void main(String[] args){
        Hashtable test = new Hashtable("C:\\Users\\Sebastian Ikin\\IntelliJIDEAProjects\\Algo øvinger\\src\\Øving_5\\navn.txt");
        String[] navn = test.getAll();
        /*for(int i = 0; i < navn.length; i++ ){
            System.out.println(navn[i]);
        }*/
        System.out.println("Søker etter meg selv: "+test.find("Ikin Sebastian Anthony "));
        System.out.println("lastfaktor: "+test.loadFactor());
        System.out.println("Gjennomsnittskollisjoner: "+test.getAvgCollisions());
    }

}
