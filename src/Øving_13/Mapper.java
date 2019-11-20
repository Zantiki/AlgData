package Øving_13;


import Øving_4.Lenke;
import Øving_7.Edge;
import Øving_7.GraphNode;
import Øving_7.Previous;
import Øving_7.Quene;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class Mapper {

    int nodesProcessed1 = 0;
    int nodesProcessed2 = 0;
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
    public MapNode aStar(MapNode start, MapNode end){
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
                processCounter++;
               // n.setLine(end.x, end.y);
                //n.setLine(end.x, end.y);
                //System.out.println(n.getLine());
                if(n.equals(end)){
                    nodesProcessed1 = processCounter;
                    //printPath(n, "aStar");
                    return end;
                }
                for(MapEdge e = n.edge1; e != null; e = e.next){
                    e.to.setLine(end.x, end.y);
                    PreviousEdge p = (PreviousEdge)e.to.d;
                    if(p.dist == p.infinity){
                        e.to.setLine(end.x, end.y);
                        //e.to.setLine(end.x, end.y);
                        p.dist = ((PreviousEdge) n.d).dist + e.time;
                        //System.out.println(e.time);
                        //n.setLine(end.x, end.y);
                        p.previous = n;
                       // n.setLine(end.x, end.y);
                        // System.out.println(n.nr+". "+"X: "+n.x+" Y: "+ n.y);
                        q.add(e.to);
                    }
                    //System.out.println(n.nodenr +"    "+e.to.nodenr+"        "+p.dist);
                }
            }
            System.out.println("Antall noder behandlet: "+processCounter);
            nodesProcessed1 = processCounter;
            return end;
            //printPath(end, "aStar");

        }catch(Exception e){
            e.printStackTrace();
            nodesProcessed1 = processCounter;
            return end;
        }

    }

    public MapNode djikstra(MapNode start, MapNode end){
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
                processCounter++;
                if(n.equals(end)){
                    //System.out.println("Processcounter "+processCounter);
                    //printPath(n, "djikstra");
                    nodesProcessed2 = processCounter;
                    return end;
                }
                for(MapEdge e = n.edge1; e != null; e = e.next){
                    PreviousEdge p = (PreviousEdge)e.to.d;
                    if(p.dist == p.infinity){
                        p.dist = ((PreviousEdge)n.d).dist + e.time;
                        //System.out.println(e.time);
                        p.previous = n;
                       // System.out.println(n.nr+". "+"X: "+n.x+" Y: "+ n.y);
                        q.add(e.to);
                    }
                    //System.out.println(n.nodenr +"    "+e.to.nodenr+"        "+p.dist);
                }
            }
            //System.out.println("Processcounter"+processCounter);
            //printPath(end, "djikstra");
            nodesProcessed1 = processCounter;
            return end;

        }catch(Exception e){
            e.printStackTrace();
            nodesProcessed1 = processCounter;
            return end;
        }
    }

    public void printPath(MapNode end, String algo) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("route_" + algo + ".txt"));
            MapNode n = end;
            int traveltime = (int)((PreviousEdge) end.d).dist/100;

            int temptime = 0;
            String time = "";
            int[] factors = {60*60, 60, 1};
            System.out.println(traveltime);
            for(int i = 0; i < factors.length; i++){
                temptime = traveltime % factors[i];
                time += ((int)(traveltime - temptime)/factors[i]) +":";
                traveltime = temptime;
            }
            while (n != null) {
                PreviousEdge previous = (PreviousEdge) n.d;
                writer.write(n.x + ", " + n.y + "\n");
                n = previous.previous;
            }
            writer.close();
            System.out.println("Traveltime "+ time + " min");
        }catch(Exception e){
            e.printStackTrace();
        }
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
            System.out.println(totEdges+" Edges added\n");
            reader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initPrevious(MapNode node){ ;
        for(int i = totNodes; i -- >0;) {
            nodes[i].d = new PreviousEdge();
            nodes[i].relDistance = 0;
        }
        ((PreviousEdge)node.d).dist = 0;
    }

    public static void main(String args[]){
        String nodeLoc = "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/norden/noder.txt";
        String edgeLoc = "http://www.iie.ntnu.no/fag/_alg/Astjerne/opg/norden/kanter.txt";
        Mapper test = new Mapper(nodeLoc, edgeLoc);
        int to = 5108028;
        int from = 5709083;
        //Bodø til københavn
        int runder = 0;
        MapNode end;
        double tid;
        Date slutt;
        Date start = new Date();
        do {
            end = test.djikstra(test.nodes[5108028], test.nodes[5709083]);
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 10000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Rute Kårvåg-Hjemnes");
        System.out.println(test.nodesProcessed2+" Noder behandlet, Millisekund pr. runde djikstra: " + tid);
        //System.out.println("Djikstra: "+test.bfsResultModifed(test.nodes[2787385], test.nodes[765228]));
        test.printPath(end, "djikstra");
        runder = 0;
        System.out.println("\n A*");
        start = new Date();
        do {
            end = test.aStar(test.nodes[5108028], test.nodes[5709083]);
            slutt = new Date();
            runder++;
        } while (slutt.getTime() - start.getTime() < 10000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Rute Kårvåg-Hjemnes");
        System.out.println(test.nodesProcessed1+" Noder behandlet, Millisekund pr. runde A*: " + tid);
        test.printPath(end, "aStar");
        //System.out.println("A*: "+test.bfsResultModifed(test.nodes[2787385], test.nodes[765228]));

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
        double r = 35285538.46153846153846153846;
        x1 = Math.toRadians(x1);
        y1 = Math.toRadians(y1);
        //System.out.println(x1);
        double x2 = Math.toRadians(this.x);
        double y2 = Math.toRadians(this.y);
        relDistance = (int)(r*Math.asin(Math.sqrt(Math.pow(Math.sin((x1-x2)*0.5), 2) +Math.cos(x2)*Math.cos(x1)
                *Math.pow(Math.sin((y1-y2)*0.5), 2))));
    }

   /* int setLine (MapNode n1, MapNode n2) {
        double sin_bredde = Math.sin((n1.x-n2.x)/2.0);
        double sin_lengde = Math.sin((n1.y-n2.y)/2.0);
        return (int) (35285538.46153846153846153846*Math.asin(Math.sqrt(
                sin_bredde*sin_bredde+n1.cos_bredde*n2.cos_bredde*sin_lengde*sin_lengde)));
    }*/



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
