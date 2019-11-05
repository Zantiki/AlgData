package Øving_11;

import java.lang.reflect.Array;
import java.util.Arrays;

class Automaton {

    private char[] inputAlphabet;
    private State[] states;

    public Automaton(char[] inputAlphabet, State[] states ){
        this.inputAlphabet = inputAlphabet;
        this.states = states;
    }

    public boolean checkInput(char[] input, State start){
        State currentState = start;
        for(int i = 0; i < input.length; i++){
            try {
                int goTo = currentState.checkInput(input[i], inputAlphabet);
                currentState = states[goTo];
                System.out.println("Went to state "+currentState.name);
            }catch(Exception e){
                   System.out.println("Invalid input!!!");
                   e.printStackTrace();
                   return false;
            }
        }
        return currentState.isAccept();
    }



    public static void main(String[] args){
        //Valid for all strings starting with 0 containing at most one 1.
        char[] inputAlphabet = {'0', '1'};
        int[][] pointers = {{1,2},{1,3},{2,2},{3,2}};
        State[] states = {
                new State(0, pointers[0]),
                new State(1, pointers[1]),
                new State(2, pointers[2]),
                new AcceptingState(3, pointers[3])
        };
        String test = "01000";
        Automaton auto = new Automaton(inputAlphabet, states);
        System.out.println("Result of "+test+": "+auto.checkInput(test.toCharArray(), states[0])+"\n\n\n");

        //Valid for all strings starting with ab or ba
        char[] inputAlphabet2 = {'a', 'b'};
        int[][] pointers2 = {{1,3},{4,2},{2,2},{2,4},{4,4}};
        State[] states2 = {
                new State(0, pointers2[0]),
                new State(1, pointers2[1]),
                new AcceptingState(2, pointers2[2]),
                new State(3, pointers2[3]),
                new State(4, pointers2[4])
        };
        String test2 = "aabb";
        Automaton auto2 = new Automaton(inputAlphabet2, states2);
        System.out.println("Result of "+test2+": "+auto2.checkInput(test2.toCharArray(), states2[0]));



    }
}

class State{
    int name;
    int[] inputPointers;

    public State(int name, int[] inputPointers){
        this.name = name;
        this.inputPointers = inputPointers;
    }


    public int checkInput(char input, char[] alphabet){
        for(int i = 0; i < inputPointers.length; i++){
            if(alphabet[i] == input){
                System.out.println("Going from "+this.name+" to "+inputPointers[i]);
                return inputPointers[i];
            }
        }
        return -1;
    }

    public boolean isAccept(){
        return false;
    }
}

class AcceptingState extends State{

    public AcceptingState(int name, int[] inputPointers){
        super(name, inputPointers);
    }

    public boolean isAccept(){
        return true;
    }

}