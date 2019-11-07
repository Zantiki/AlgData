package Øving_11;

import java.util.Arrays;

class Automaton {

    private char[] inputAlphabet;
    private State[] states;
    private int[][] pointers;

    public Automaton(char[] inputAlphabet, int[][] pointers, int[] acceptingStates ){
        this.inputAlphabet = inputAlphabet;
        this.pointers = pointers;
        this.states = setStates(acceptingStates);
    }

    private State[] setStates(int[] acceptingStates){
        State[] states = new State[pointers.length];
        for(int i = 0; i < states.length; i++){
            states[i] = new State(i, pointers[i]);
            for(int y = 0; y < acceptingStates.length; y++){
                if(i == acceptingStates[y]){
                    states[i] = new AcceptingState(i, pointers[i]);
                    System.out.println(i+" is an accepting state");
                }
            }
        }
        return states;
    }

    public boolean checkInput(char[] input, int start){
        State currentState = null;
        try{
            currentState = states[start];
        }catch(Exception e){
            System.out.println("Start does not exist in this automaton");
            e.printStackTrace();
            return false;
        }
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
        int[] accept = {3};
        String test = "01000";
        Automaton auto = new Automaton(inputAlphabet, pointers, accept);
        System.out.println("Result of "+test+": "+auto.checkInput(test.toCharArray(), 0)+"\n\n\n");

        //Valid for all strings starting with ab or ba
        char[] inputAlphabet2 = {'a', 'b'};
        int[][] pointers2 = {{1,3},{4,2},{2,2},{2,4},{4,4}};
        int[] accept2 = {2};
        String test2 = "aabb";
        Automaton auto2 = new Automaton(inputAlphabet2, pointers2, accept2);
        System.out.println("Result of "+test2+": "+auto2.checkInput(test2.toCharArray(), 0));



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