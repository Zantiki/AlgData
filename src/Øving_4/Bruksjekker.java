package Øving_4;
/*Declare a character stack S.
Now traverse the expression string exp.
If the current character is a starting bracket (‘(‘ or ‘{‘ or ‘[‘) then push it to stack.
If the current character is a closing bracket (‘)’ or ‘}’ or ‘]’)
then pop from stack and if the popped character is the matching starting bracket then fine else parenthesis are not balanced.
After complete traversal, if there is some starting bracket left in stack then “not balanced”*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Bruksjekker {
    final String filnavn;
    char[] kode;

    public Bruksjekker(String filnavn){
        this.filnavn = filnavn;
        lesfrafil();
        System.out.println("kode lest");
    }

    private void lesfrafil(){
        try{
            FileReader con = new FileReader(filnavn);
            BufferedReader leser = new BufferedReader(con);
            String kodeString = "";
            String linje = "";
            while(linje != null){
                linje = leser.readLine();
                kodeString+=linje;
            }
            leser.close();
            con.close();
            kode = kodeString.toCharArray();
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    static boolean match(char b1, char b2) {
        if (b1 == '(' && b2 == ')')
            return true;
        else if (b1 == '{' && b2 == '}')
            return true;
        else if (b1 == '[' && b2 == ']')
            return true;
        else
            return false;
    }

    public boolean algoritme() {
        Stakk s = new Stakk(kode.length);

        //Let etter og push push tegn på stacken.
        for (int i = 0; i < kode.length; i++) {
            if (kode[i] == '{' || kode[i] == '(' || kode[i] == '[')
                s.push(kode[i]);
          //Sjekk om parene matcher
            if (kode[i] == '}' || kode[i] == ')' || kode[i] == ']') {
                if (s.tom()) {
                    return false;
                }
                //Hent øverste element fra stakk og sjekk om det matcher nåværende element.
                else if (!match((char) s.pop(), kode[i])) {
                    return false;
                }
            }

        }
        if (s.tom())
            return true;
        else
        {
            return false;
        }
    }

    public static void main(String[] args){
        Bruksjekker test1 = new Bruksjekker("C:\\Users\\Sebastian Ikin\\IntelliJIDEAProjects\\Algo øvinger\\src\\Øving_4\\Testprogrammer\\Node.java");
        Bruksjekker test2 = new Bruksjekker("C:\\Users\\Sebastian Ikin\\IntelliJIDEAProjects\\Algo øvinger\\src\\Øving_4\\Testprogrammer\\Main.java");
        Bruksjekker test3 = new Bruksjekker("C:\\Users\\Sebastian Ikin\\IntelliJIDEAProjects\\Algo øvinger\\src\\Øving_4\\Testprogrammer\\Iterator.java");
        Bruksjekker test4 = new Bruksjekker("C:\\Users\\Sebastian Ikin\\IntelliJIDEAProjects\\Algo øvinger\\src\\Øving_4\\Node.java");
        Bruksjekker test5 = new Bruksjekker("C:\\Users\\Sebastian Ikin\\IntelliJIDEAProjects\\Algo øvinger\\src\\Øving_4\\Main.java");
        Bruksjekker test6 = new Bruksjekker("C:\\Users\\Sebastian Ikin\\IntelliJIDEAProjects\\Algo øvinger\\src\\Øving_4\\Iterator.java");
        System.out.println("test 1 false vs "+test1.algoritme());
        System.out.println("test 2 false vs "+test2.algoritme());
        System.out.println("test 3 false vs "+test3.algoritme());
        System.out.println("test 4 true vs "+test4.algoritme());
        System.out.println("test 5 true vs "+test5.algoritme());
        System.out.println("test 6 true vs "+test6.algoritme());

    }
}
