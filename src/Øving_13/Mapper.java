package Øving_13;


import Øving_7.Edge;
import Øving_7.GraphNode;
import Øving_7.Previous;
import Øving_7.Quene;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Mapper {

    int totNodes = 0;
    int totEdges = 0;
    MapNode[] nodes = null;

    public Mapper(String nodeLoc, String edgeLoc){
        readGraphFromURL(nodeLoc, edgeLoc);
    }

    /*public void djikstra(MapNode s){
        initPrevious(s);
        System.out.println("Previous initialized in djikstra");
        MapNode[] pri = new MapNode[totNodes];
        for(int i = totNodes; i > 1; --i){
            MapNode n = getMin(i, pri);
            for(MapEdge e = (MapEdge)n.edge1; e != null; e = e.next){
                shorten(n, e);
            }
        }
    }*/
    public void aStar(MapNode start, MapNode end){
        initPrevious(start);
        boolean countNodes = true;
        int processCounter = 0;
        //Quene q = new Quene(totNodes -1);
        Comparator<MapNode> distance = Comparator.comparing(MapNode::getLine);
        PriorityQueue<MapNode> q = new PriorityQueue<>(distance);
        start.setLine(end.x, end.y);
        q.add(start);
        try {

            while(!q.isEmpty()){
                MapNode n = (MapNode)q.poll();
               // n.setLine(end.x, end.y);
                //n.setLine(end.x, end.y);
                //System.out.println(n.getLine());
                if(n.equals(end)){
                    System.out.println("Processcounter "+processCounter);
                    printPath(n);
                    return;
                }
                for(MapEdge e = n.edge1; e != null; e = e.next){
                    e.to.setLine(end.x, end.y);
                    PreviousEdge p = (PreviousEdge)e.to.d;
                    if(p.dist == p.infinity){
                        //e.to.setLine(end.x, end.y);
                        p.dist = ((PreviousEdge)n.d).dist + e.time;
                        //System.out.println(e.time);
                        //n.setLine(end.x, end.y);
                        p.previous = n;
                       // n.setLine(end.x, end.y);
                        // System.out.println(n.nr+". "+"X: "+n.x+" Y: "+ n.y);
                        q.add(e.to);
                        processCounter++;
                    }
                    //System.out.println(n.nodenr +"    "+e.to.nodenr+"        "+p.dist);
                }
            }
            System.out.println(processCounter);
            printPath(end);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void djikstra(MapNode start, MapNode end){
        initPrevious(start);
        boolean countNodes = true;
        int processCounter = 0;
        //Quene q = new Quene(totNodes -1);
        Comparator<MapNode> distance = Comparator.comparing(MapNode::getDistance);
        PriorityQueue<MapNode> q = new PriorityQueue<>(distance);
        q.add(start);
        try {

            while(!q.isEmpty()){
                MapNode n = (MapNode)q.poll();
                if(n.equals(end)){
                    System.out.println("Processcounter "+processCounter);
                    printPath(n);
                    return;
                }
                for(MapEdge e = n.edge1; e != null; e = e.next){
                    PreviousEdge p = (PreviousEdge)e.to.d;
                    if(p.dist == p.infinity){
                        p.dist = ((PreviousEdge)n.d).dist + e.time;
                        //System.out.println(e.time);
                        p.previous = n;
                       // System.out.println(n.nr+". "+"X: "+n.x+" Y: "+ n.y);
                        q.add(e.to);
                        processCounter++;
                    }
                    //System.out.println(n.nodenr +"    "+e.to.nodenr+"        "+p.dist);
                }
            }
            System.out.println("Processcounter"+processCounter);
            printPath(end);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void printPath(MapNode end) throws Exception{
        BufferedWriter writer = new BufferedWriter(new FileWriter("route.txt"));
        MapNode n = end;
        int traveltime = ((PreviousEdge)end.d).dist;
        while(n != null){
            PreviousEdge previous = (PreviousEdge)n.d;
            writer.write(n.x+","+n.y+"\n");
            n = previous.previous;
        }
        writer.close();
        System.out.println("Traveltime "+(traveltime/(100*60*60)));
    }

    public String bfsResultModifed(MapNode start, MapNode end) {
        //bfsModified(start,end);
        String res = "From To Intersections\n";
        for (int i = 0; i < totNodes; i++) {
            if (i == end.nr) {
                res += nodes[i].nr + "    " + ((PreviousEdge) nodes[i].d).previous.nr + "        " + ((PreviousEdge) nodes[i].d).dist + "\n";
                // res += start.nodenr + "    " + end.nodenr+ "        " + (int) start.d + "\n";
            }
        }
        return res;
    }


    public void shorten(MapNode n, MapEdge e){
        PreviousEdge nd = (PreviousEdge)n.d, md = (PreviousEdge)e.to.d;
        if(md.dist > nd.dist + e.time){
            md.dist = nd.dist + e.time;
            md.previous = n;
        }
    }

    public void aStar(String url){

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
                st = new StringTokenizer(reader.readLine());
                String number = st.nextToken();
                String x = st.nextToken();
                String y = st.nextToken();
                nodes[i] = new MapNode(Integer.parseInt(number), Double.parseDouble(x), Double.parseDouble(y));
                //System.out.println("Node "+i+" added");
            }
            System.out.println(nodes.length+" Nodes added");
            url = new URL(edgeLoc);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            st = new StringTokenizer(reader.readLine());
            totEdges = Integer.parseInt(st.nextToken());
            for(int i = 0; i < totEdges; i++){
                st = new StringTokenizer(reader.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                //int dist = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());
                //System.out.println(time);
                MapEdge edge = new MapEdge(nodes[from].edge1, nodes[to], time);
               // st.nextToken();
                // st.nextToken();
                nodes[from].edge1 = edge;
                //System.out.println("An edge from "+from+" to "+to+" has been added");
            }
            System.out.println(totEdges+" Edges added");
            reader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initPrevious(MapNode node){ ;
        for(int i = totNodes; i -- >0;) {
            nodes[i].d = new PreviousEdge();
        }
        ((PreviousEdge)node.d).dist = 0;
    }

    public static void main(String args[]){
        String nodeLoc = "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/norden/noder.txt";
        String edgeLoc = "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/norden/kanter.txt";
        Mapper test = new Mapper(nodeLoc, edgeLoc);
        //Rekjavik til melar
        test.djikstra(test.nodes[2787385], test.nodes[765228]);
        System.out.println("Djikstra: "+test.bfsResultModifed(test.nodes[2419175], test.nodes[5177564]));
        //test = new Mapper(nodeLoc, edgeLoc);
        test.aStar(test.nodes[2787385], test.nodes[765228]);
        System.out.println("A*: "+test.bfsResultModifed(test.nodes[2787385], test.nodes[765228]));
        /*test.bfsModified(test.nodes[0], test.nodes[1]);
        System.out.println(test.bfsResultModifed(test.nodes[0], test.nodes[1]));*/
    }
}

class MapNode{
    int nr;
    double x;
    double y;
    Object d;
    MapEdge edge1;
    long relDistance = 0;
    //ArrayList<MapEdge> edges = new ArrayList<>();

    public MapNode(int nr, double x, double y){
        this.nr = nr;
        this.x = x;
        this.y = y;
    }

    public int getDistance(){
      return ((PreviousEdge)d).dist;
    }

    public void setLine(double x1, double y1){
        double r = 6371 * 1000;
        x1 = Math.toRadians(x1);
        y1 = Math.toRadians(y1);
        //System.out.println(x1);
        double x2 = Math.toRadians(this.x);
        double y2 = Math.toRadians(this.y);
        this.relDistance = Math.round(2*r*Math.asin(Math.sqrt(Math.pow(Math.sin((x1-x2)*0.5), 2) +Math.cos(x2)*Math.cos(x1)
                *Math.pow(Math.sin((y1-y2)*0.5), 2))));
    }

    public double getLine(){
        //System.out.println(relDistance);
        return relDistance + getDistance();
    }
}

class MapEdge{
    MapEdge next;
    MapNode to;
    //int dist;
    int time;

    public MapEdge(MapEdge next, MapNode to, int time){
        this.next = next;
        this.to = to;
        //this.dist = dist;
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
