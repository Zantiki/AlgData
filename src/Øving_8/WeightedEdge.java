package Øving_8;

public class WeightedEdge {
    int to;
    int from;
    int capacity;
    int flow;
    WeightedEdge inverted;

    public WeightedEdge(int from, int to, int capacity, int flow){
        this.to = to;
        this.from = from;
        this.flow = flow;
        this.capacity = capacity;
    }
    public void setInverted(WeightedEdge e){
        inverted = e;
    }

}
