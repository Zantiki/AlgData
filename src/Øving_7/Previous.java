package Øving_7;

public class Previous {
    int dist;
    GraphNode previous;
    static int infinity = 10000000;

    public Previous(){
        dist = infinity;
    }
    public int getDist(){
        return dist;
    }

    public GraphNode getPrevious(){
        return previous;
    }

}
