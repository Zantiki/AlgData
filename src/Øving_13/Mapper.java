package Øving_13;


import Øving_7.Edge;
import Øving_7.GraphNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Mapper {

    int totNodes = 0;
    int totEdges = 0;
    MapNode[] nodes = null;

    public Mapper(String nodeLoc, String edgeLoc){
        readGraphFromURL(nodeLoc, edgeLoc);
    }

    public static void djikstra(String url){

    }

    public static void aStar(String url){

    }

    private String writePath(){
        return null;
    }

    public void readGraphFromURL(String nodeLoc, String edgeLoc){
        try {
            URL url = new URL(nodeLoc);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringTokenizer st = new StringTokenizer(reader.readLine());
            totNodes = Integer.parseInt(st.nextToken());
            nodes = new MapNode[totNodes];
            for(int i = 0; i < totNodes; ++i){
                String number = st.nextToken();
                String x = st.nextToken();
                String y = st.nextToken();
                nodes[i] = new MapNode(Integer.parseInt(number), Double.parseDouble(x), Double.parseDouble(y));
                //System.out.println("Node "+i+" added");
            }
            System.out.println()
            url = new URL(edgeLoc);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            st = new StringTokenizer(reader.readLine());
            totEdges = Integer.parseInt(st.nextToken());
            for(int i = 0; i < totEdges; i++){
                st = new StringTokenizer(reader.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int dist = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());
                MapEdge edge = new MapEdge(nodes[from].edge1, nodes[to], dist, time);
                nodes[from].edge1 = edge;
                // System.out.println("An edge from "+from+" to "+to+" has been added");
            }
            reader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initPrevious(MapNode node){
        for(int i = totNodes; i -- >0;) {
            nodes[i].d = new PreviousEdge();
        }
        ((PreviousEdge)node.d).dist = 0;
    }

    public static void main(String args[]){
        String nodeLoc = "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/island/noder.txt";
        String edgeLoc = "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/island/kanter.txt";
        Mapper test = new Mapper();
        test.readGraphFromURL();
    }
}

class MapNode{
    int nr;
    double x;
    double y;
    Object d;
    MapEdge edge1;
    //ArrayList<MapEdge> edges = new ArrayList<>();

    public MapNode(int nr, double x, double y){
        this.nr = nr;
        this.x = x;
        this.y = y;
    }
}

class MapEdge{
    MapEdge to;
    MapNode from;
    int dist;
    int time;

    public MapEdge(MapEdge to, MapNode from, int dist, int time){
        this.to = to;
        this.from = from;
        this.dist = dist;
        this.time = time;
    }
}

class PreviousEdge {
    int dist;
    MapNode previous;
    static int infinity = 10000000;

    public PreviousEdge(){
        dist = infinity;
    }
    public int getDist(){
        return dist;
    }

    public MapNode getPrevious(){
        return previous;
    }

}
