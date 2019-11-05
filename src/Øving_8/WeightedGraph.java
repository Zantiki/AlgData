package Øving_8;

import Øving_7.Edge;
import Øving_7.Graph;
import Øving_7.GraphNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


public class WeightedGraph {
    int totNodes = 0;
    int totEdges = 0;
    WeightedNode[] nodes = null;

    public void readGraphFromURL(String loc){
        try {
            URL url = new URL(loc);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringTokenizer st = new StringTokenizer(reader.readLine());
            totNodes = Integer.parseInt(st.nextToken());
            nodes = new WeightedNode[totNodes];
            for(int i = 0; i < totNodes; ++i){
                nodes[i] = new WeightedNode(i);
                //System.out.println("Node "+i+" added");
            }
            totEdges = Integer.parseInt(st.nextToken());
            for(int i = 0; i < totEdges; i++){
                st = new StringTokenizer(reader.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int capacity = Integer.parseInt(st.nextToken());
                WeightedEdge edge1 = new WeightedEdge(from, to, capacity, 0);
                WeightedEdge edge2 = new WeightedEdge(to, from, 0, 0);
                edge1.setInverted(edge2);
                edge2.setInverted(edge1);
                nodes[from].edges.add(edge1);
                nodes[to].edges.add(edge2);
                // System.out.println("An edge from "+from+" to "+to+" has been added");
            }
            reader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void edmondsKarp(int source, int sink){
        WeightedNode src = null;
        WeightedNode snk = null;

        //check if source/sink exists and act accordingly
        for(int i = 0; i < totEdges; i++){
            if(src != null && snk != null){
                break;
            }
            if(nodes[i].nodenr == source){
                src = nodes[i];
            }
            if(nodes[i].nodenr == sink){
                snk = nodes[i];
            }
        }
        if(src == null || snk == null){
            System.out.println("Source or sink does not exist");
            return;
        }
        int maxFlow = 0;
        while(true) {
            //Modified BFS for finding shortest augmenting path
            WeightedEdge[] parent = new WeightedEdge[totEdges];
            ArrayList<WeightedNode> q = new ArrayList<>();
            q.add(nodes[source]);

            while (!q.isEmpty()) {
                WeightedNode current = q.remove(0);
                for (int i = 0; i < current.edges.size(); i++) {
                    WeightedEdge edge = current.edges.get(i);
                    if (parent[edge.to] == null && edge.to != source && edge.capacity > edge.flow) {
                        parent[edge.to] = edge;
                        q.add(nodes[edge.to]);
                    }
                }
            }

            if (parent[sink] == null) {
                break;
            }
            int pushFlow = Integer.MAX_VALUE;

            //start at the sink and go to the next edge until the source/null is reached
            for(WeightedEdge e = parent[sink]; e != null; e = parent[e.from]){
                //Push the smallest flow value
                pushFlow = Math.min(pushFlow, e.capacity-e.flow);
            }

            //Each node passed in a walk.
            ArrayList<Integer> road = new ArrayList<Integer>();

            //start at the sink and go to the next edge until the source/null is reached
            for(WeightedEdge e = parent[sink]; e != null; e = parent[e.from]){
                //add the pushFlow to the edges flow.
                e.flow += pushFlow;
                road.add(e.to);

                //Inverse of what we do above
                e.inverted.flow -= pushFlow;
            }
            //Add the flow to the maxFlow
            maxFlow += pushFlow;
            String roadString = "" + source;
            //Create a string based on the road
            while(!road.isEmpty()){
                roadString += " "+road.remove(road.size()-1);
            }

            System.out.println(pushFlow + "     "+roadString);

        }
        System.out.println("Max Flow: "+maxFlow);
    }

    public WeightedNode[] getNodes(){
        return nodes;
    }
    public static void main(String args[]){
        WeightedGraph test = new WeightedGraph();
        test.readGraphFromURL("http://www.iie.ntnu.no/fag/_alg/v-graf/flytgraf1");
        WeightedNode[] nodes = test.getNodes();
        System.out.println("Maksimal flyt av flytgraf1");
        test.edmondsKarp(1, 6);
    }
}
