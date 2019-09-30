package Øving_7;

public class Edge {
    Edge next;
    GraphNode to;

    public Edge(GraphNode to, Edge next){
        this.next = next;
        this.to = to;
    }
}
