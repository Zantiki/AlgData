package Øving_7;

import Øving_4.Node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class Graph {
    int totNodes = 0;
    int totEdges = 0;
    GraphNode[] nodes = null;

    public void bfs(GraphNode start){
        initPrevious(start);
        Quene q = new Quene(totNodes -1);
        q.addToQuene(start);
        while(!q.empty()){
            GraphNode n = (GraphNode)q.nextInQuene();
            for(Edge e = n.edge1; e != null; e = e.next){
                Previous p = (Previous)e.to.d;
                if(p.dist == p.infinity){
                    p.dist = ((Previous)n.d).dist +1;
                    p.previous = n;
                    q.addToQuene(e.to);
                }
                System.out.println("Node "+n.nodenr +" to node "+e.to.nodenr+", distance "+p.dist);
            }
        }

    }

    public void initPrevious(GraphNode node){
        for(int i = totNodes; i -- >0;) {
            nodes[i].d = new Previous();
        }
        ((Previous)node.d).dist = 0;
    }

    public void readFromURL(String loc){
        try {
            URL url = new URL(loc);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringTokenizer st = new StringTokenizer(reader.readLine());
            totNodes = Integer.parseInt(st.nextToken());
            nodes = new GraphNode[totNodes];
            for(int i = 0; i < totNodes; i++){
                nodes[i] = new GraphNode(i);
                System.out.println("Node "+i+" added");
            }
            totEdges = Integer.parseInt(st.nextToken());
            for(int i = 0; i < totEdges; i++){
                st = new StringTokenizer(reader.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                Edge edge = new Edge(nodes[to], nodes[from].edge1);
                nodes[from].edge1 = edge;
                System.out.println("An edge from "+from+" to "+to+" has been added");
            }
            reader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Graph test = new Graph();
        test.readFromURL("http://www.iie.ntnu.no/fag/_alg/uv-graf/L7g1");
        test.bfs(test.nodes[0]);
    }
}